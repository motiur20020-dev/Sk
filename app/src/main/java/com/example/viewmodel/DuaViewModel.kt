package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.dua.DuaRepository
import com.example.data.local.AppDatabase
import com.example.data.local.entities.BookmarkEntity
import com.example.model.DuaCategory
import com.example.model.DuaItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DuaViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val bookmarkDao = db.bookmarkDao()

    val categories: List<DuaCategory> = DuaRepository.CATEGORIES
    val allDuas: List<DuaItem> = DuaRepository.ALL_DUAS

    val bookmarkedDuas: StateFlow<List<BookmarkEntity>> = bookmarkDao.getBookmarksByType("DUA")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedCategoryId = MutableStateFlow<String?>(null) // null means "All"
    val selectedCategoryId: StateFlow<String?> = _selectedCategoryId.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun selectCategory(catId: String?) {
        _selectedCategoryId.value = catId
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun isDuaFavorite(duaId: String): Boolean {
        return bookmarkedDuas.value.any { it.itemId == duaId }
    }

    fun toggleFavorite(dua: DuaItem) {
        viewModelScope.launch {
            val isFav = isDuaFavorite(dua.id)
            if (isFav) {
                bookmarkDao.deleteByItemId("DUA", dua.id)
            } else {
                bookmarkDao.insertBookmark(
                    BookmarkEntity(
                        type = "DUA",
                        itemId = dua.id,
                        title = dua.titleEn,
                        subtitle = dua.titleBn,
                        arabicText = dua.arabicText,
                        translation = dua.meaningEn
                    )
                )
            }
        }
    }
}
