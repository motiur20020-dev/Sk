package com.example.viewmodel

import android.app.Application
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.entities.BookmarkEntity
import com.example.data.local.entities.LastReadEntity
import com.example.data.quran.QuranRepository
import com.example.model.Ayah
import com.example.model.JuzInfo
import com.example.model.Surah
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuranViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val bookmarkDao = db.bookmarkDao()
    private val lastReadDao = db.lastReadDao()

    val lastRead: StateFlow<LastReadEntity?> = lastReadDao.getLastRead()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val bookmarkedAyahs: StateFlow<List<BookmarkEntity>> = bookmarkDao.getBookmarksByType("QURAN")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedTab = MutableStateFlow(0) // 0: Surah, 1: Juz
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    private val _currentSurah = MutableStateFlow<Surah?>(null)
    val currentSurah: StateFlow<Surah?> = _currentSurah.asStateFlow()

    private val _currentAyahs = MutableStateFlow<List<Ayah>>(emptyList())
    val currentAyahs: StateFlow<List<Ayah>> = _currentAyahs.asStateFlow()

    // Audio Recitation state
    private val _isPlayingAudio = MutableStateFlow(false)
    val isPlayingAudio: StateFlow<Boolean> = _isPlayingAudio.asStateFlow()

    private val _isBufferingAudio = MutableStateFlow(false)
    val isBufferingAudio: StateFlow<Boolean> = _isBufferingAudio.asStateFlow()

    private val _activePlayingSurah = MutableStateFlow<Int?>(null)
    val activePlayingSurah: StateFlow<Int?> = _activePlayingSurah.asStateFlow()

    private var mediaPlayer: MediaPlayer? = null

    val allSurahs: List<Surah> = QuranRepository.SURAH_LIST
    val allJuz: List<JuzInfo> = QuranRepository.JUZ_LIST

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedTab(tab: Int) {
        _selectedTab.value = tab
    }

    fun openSurah(surahNumber: Int) {
        val surah = allSurahs.firstOrNull { it.number == surahNumber } ?: return
        _currentSurah.value = surah
        val ayahs = QuranRepository.getAyahsForSurah(surahNumber)
        _currentAyahs.value = ayahs

        // Save last read automatically
        viewModelScope.launch {
            lastReadDao.saveLastRead(
                LastReadEntity(
                    id = 1,
                    surahNumber = surah.number,
                    surahNameEn = surah.nameEnglish,
                    surahNameBn = surah.nameBengali,
                    ayahNumber = 1
                )
            )
        }
    }

    fun toggleBookmarkAyah(surah: Surah, ayah: Ayah) {
        viewModelScope.launch {
            val itemId = "${surah.number}:${ayah.numberInSurah}"
            val existing = bookmarkedAyahs.value.firstOrNull { it.itemId == itemId }
            if (existing != null) {
                bookmarkDao.deleteByItemId("QURAN", itemId)
            } else {
                bookmarkDao.insertBookmark(
                    BookmarkEntity(
                        type = "QURAN",
                        itemId = itemId,
                        title = "${surah.nameEnglish} : Ayah ${ayah.numberInSurah}",
                        subtitle = "${surah.nameBengali} : আয়াত ${ayah.numberInSurah}",
                        arabicText = ayah.textArabic,
                        translation = ayah.textEnglish,
                        surahNumber = surah.number,
                        ayahNumber = ayah.numberInSurah
                    )
                )
            }
        }
    }

    fun isAyahBookmarked(surahNumber: Int, ayahNumber: Int): Boolean {
        val itemId = "$surahNumber:$ayahNumber"
        return bookmarkedAyahs.value.any { it.itemId == itemId }
    }

    fun playSurahAudio(surahNumber: Int) {
        if (_isPlayingAudio.value && _activePlayingSurah.value == surahNumber) {
            mediaPlayer?.pause()
            _isPlayingAudio.value = false
            return
        }

        if (_activePlayingSurah.value == surahNumber && mediaPlayer != null) {
            mediaPlayer?.start()
            _isPlayingAudio.value = true
            return
        }

        stopAudio()
        _isBufferingAudio.value = true
        _activePlayingSurah.value = surahNumber

        val url = QuranRepository.getSurahAudioUrl(surahNumber)
        try {
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(url)
                setOnPreparedListener {
                    _isBufferingAudio.value = false
                    start()
                    _isPlayingAudio.value = true
                }
                setOnCompletionListener {
                    _isPlayingAudio.value = false
                    _activePlayingSurah.value = null
                }
                setOnErrorListener { _, _, _ ->
                    _isBufferingAudio.value = false
                    _isPlayingAudio.value = false
                    _activePlayingSurah.value = null
                    false
                }
                prepareAsync()
            }
        } catch (e: Exception) {
            _isBufferingAudio.value = false
            _isPlayingAudio.value = false
        }
    }

    fun stopAudio() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        _isPlayingAudio.value = false
        _isBufferingAudio.value = false
        _activePlayingSurah.value = null
    }

    fun nextSurah() {
        val current = _currentSurah.value ?: return
        if (current.number < 114) {
            openSurah(current.number + 1)
        }
    }

    fun previousSurah() {
        val current = _currentSurah.value ?: return
        if (current.number > 1) {
            openSurah(current.number - 1)
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopAudio()
    }
}
