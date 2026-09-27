package com.example.viewmodel

import android.app.Application
import android.content.Context
import android.location.Location
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.calendar.HijriDate
import com.example.data.calendar.IslamicCalendarHelper
import com.example.data.local.PreferencesManager
import com.example.model.AppLanguage
import com.example.model.AppThemeMode
import com.example.model.AsrMethod
import com.example.model.CalculationMethod
import com.example.model.PrayerName
import com.example.model.PrayerSchedule
import com.example.model.UserLocation
import com.example.prayer.PrayerNotificationManager
import com.example.prayer.PrayerTimeCalculator
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val preferencesManager = PreferencesManager(application)
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    // Current navigation tab
    private val _currentTab = MutableStateFlow("home")
    val currentTab: StateFlow<String> = _currentTab.asStateFlow()

    // Sub-screen for "more" or details: "main", "dua", "tasbih", "calendar", "ramadan", "bookmarks", "settings", "surah_detail"
    private val _currentSubScreen = MutableStateFlow<String?>(null)
    val currentSubScreen: StateFlow<String?> = _currentSubScreen.asStateFlow()

    private val _selectedSurahNumber = MutableStateFlow<Int?>(null)
    val selectedSurahNumber: StateFlow<Int?> = _selectedSurahNumber.asStateFlow()

    // Prayer Schedule
    private val _prayerSchedule = MutableStateFlow<PrayerSchedule?>(null)
    val prayerSchedule: StateFlow<PrayerSchedule?> = _prayerSchedule.asStateFlow()

    // Current Hijri Date
    private val _hijriDate = MutableStateFlow(IslamicCalendarHelper.getHijriDate(Date()))
    val hijriDate: StateFlow<HijriDate> = _hijriDate.asStateFlow()

    // Adhan playback state
    private val _isAdhanPlaying = MutableStateFlow(false)
    val isAdhanPlaying: StateFlow<Boolean> = _isAdhanPlaying.asStateFlow()
    private var mediaPlayer: MediaPlayer? = null

    // Permission dialog states
    private val _showLocationExplanationDialog = MutableStateFlow(false)
    val showLocationExplanationDialog: StateFlow<Boolean> = _showLocationExplanationDialog.asStateFlow()

    private val _showNotificationExplanationDialog = MutableStateFlow(false)
    val showNotificationExplanationDialog: StateFlow<Boolean> = _showNotificationExplanationDialog.asStateFlow()

    private var countdownJob: Job? = null

    // Popular cities preset for easy manual selection
    val popularCities = listOf(
        UserLocation(23.8103, 90.4125, "Dhaka, Bangladesh", "ঢাকা, বাংলাদেশ", false),
        UserLocation(22.3569, 91.7832, "Chittagong, Bangladesh", "চট্টগ্রাম, বাংলাদেশ", false),
        UserLocation(24.8949, 91.8687, "Sylhet, Bangladesh", "সিলেট, বাংলাদেশ", false),
        UserLocation(24.3636, 88.6241, "Rajshahi, Bangladesh", "রাজশাহী, বাংলাদেশ", false),
        UserLocation(22.8456, 89.5403, "Khulna, Bangladesh", "খুলনা, বাংলাদেশ", false),
        UserLocation(21.4225, 39.8262, "Makkah, Saudi Arabia", "মক্কা, সৌদি আরব", false),
        UserLocation(24.5247, 39.5692, "Madinah, Saudi Arabia", "মদীনা, সৌদি আরব", false),
        UserLocation(51.5074, -0.1278, "London, United Kingdom", "লন্ডন, যুক্তরাজ্য", false),
        UserLocation(40.7128, -74.0060, "New York, USA", "নিউইয়র্ক, যুক্তরাষ্ট্র", false),
        UserLocation(3.1390, 101.6869, "Kuala Lumpur, Malaysia", "কুয়ালালামপুর, মালয়েশিয়া", false),
        UserLocation(-6.2088, 106.8456, "Jakarta, Indonesia", "জাকার্তা, ইন্দোনেশিয়া", false),
        UserLocation(41.0082, 28.9784, "Istanbul, Turkey", "ইস্তাম্বুল, তুরস্ক", false),
        UserLocation(24.8607, 67.0011, "Karachi, Pakistan", "করাচি, পাকিস্তান", false),
        UserLocation(25.2048, 55.2708, "Dubai, UAE", "দুবাই, সংযুক্ত আরব আমিরাত", false)
    )

    init {
        PrayerNotificationManager.initNotificationChannels(application)
        refreshPrayerSchedule()
        startCountdownTicker()
    }

    fun navigateToTab(tab: String) {
        _currentTab.value = tab
        _currentSubScreen.value = null
    }

    fun openSubScreen(screenName: String, surahNumber: Int? = null) {
        _selectedSurahNumber.value = surahNumber
        _currentSubScreen.value = screenName
    }

    fun navigateBack(): Boolean {
        if (_currentSubScreen.value != null) {
            _currentSubScreen.value = null
            _selectedSurahNumber.value = null
            return true
        }
        if (_currentTab.value != "home") {
            _currentTab.value = "home"
            return true
        }
        return false
    }

    fun setShowLocationExplanation(show: Boolean) {
        _showLocationExplanationDialog.value = show
    }

    fun setShowNotificationExplanation(show: Boolean) {
        _showNotificationExplanationDialog.value = show
    }

    fun refreshPrayerSchedule() {
        val loc = preferencesManager.userLocation.value
        val calc = preferencesManager.calculationMethod.value
        val asr = preferencesManager.asrMethod.value
        val lang = preferencesManager.language.value

        val hijri = IslamicCalendarHelper.getHijriDate(Date())
        _hijriDate.value = hijri

        val notifMap = mapOf(
            PrayerName.FAJR to preferencesManager.fajrNotif.value,
            PrayerName.DHUHR to preferencesManager.dhuhrNotif.value,
            PrayerName.ASR to preferencesManager.asrNotif.value,
            PrayerName.MAGHRIB to preferencesManager.maghribNotif.value,
            PrayerName.ISHA to preferencesManager.ishaNotif.value
        )

        val cityName = if (lang == AppLanguage.BENGALI) loc.cityNameBn else loc.cityNameEn
        val hijriFormatted = if (lang == AppLanguage.BENGALI) hijri.formattedBn else hijri.formattedEn

        val schedule = PrayerTimeCalculator.buildPrayerSchedule(
            date = Date(),
            location = loc,
            calcMethod = calc,
            asrMethod = asr,
            hijriDateString = hijriFormatted,
            cityName = cityName,
            notifMap = notifMap
        )
        _prayerSchedule.value = schedule

        PrayerNotificationManager.schedulePrayerAlarms(getApplication(), preferencesManager)
    }

    private fun startCountdownTicker() {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            while (isActive) {
                delay(1000)
                refreshPrayerSchedule()
            }
        }
    }

    fun updateLocation(location: UserLocation) {
        preferencesManager.setLocation(location)
        refreshPrayerSchedule()
    }

    fun detectGpsLocation(onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { loc: Location? ->
                if (loc != null) {
                    val userLoc = UserLocation(
                        latitude = loc.latitude,
                        longitude = loc.longitude,
                        cityNameEn = "Detected Location (${String.format("%.2f", loc.latitude)}, ${String.format("%.2f", loc.longitude)})",
                        cityNameBn = "স্বয়ংক্রিয় অবস্থান (${String.format("%.2f", loc.latitude)}, ${String.format("%.2f", loc.longitude)})",
                        isGps = true
                    )
                    updateLocation(userLoc)
                    onSuccess(userLoc.cityNameEn)
                } else {
                    onError("Could not retrieve current GPS coordinates. Please ensure GPS is enabled.")
                }
            }.addOnFailureListener { e ->
                onError(e.localizedMessage ?: "Location fetch failed")
            }
        } catch (e: SecurityException) {
            onError("Location permission not granted")
        }
    }

    fun playAdhanPreview() {
        if (_isAdhanPlaying.value) {
            stopAdhanPreview()
            return
        }
        try {
            mediaPlayer?.release()
            // Sample online high-quality Makkah Adhan
            val adhanUrl = "https://www.islamcan.com/audio/adhan/azan1.mp3"
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(adhanUrl)
                setOnPreparedListener {
                    start()
                    _isAdhanPlaying.value = true
                }
                setOnCompletionListener {
                    _isAdhanPlaying.value = false
                }
                setOnErrorListener { _, _, _ ->
                    _isAdhanPlaying.value = false
                    false
                }
                prepareAsync()
            }
        } catch (e: Exception) {
            _isAdhanPlaying.value = false
        }
    }

    fun stopAdhanPreview() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        _isAdhanPlaying.value = false
    }

    override fun onCleared() {
        super.onCleared()
        countdownJob?.cancel()
        stopAdhanPreview()
    }
}
