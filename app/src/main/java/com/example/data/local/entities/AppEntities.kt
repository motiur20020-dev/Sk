package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasbih_records")
data class TasbihEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dhikrName: String,
    val count: Int,
    val target: Int,
    val dateFormatted: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String, // "QURAN" or "DUA"
    val itemId: String,
    val title: String,
    val subtitle: String,
    val arabicText: String,
    val translation: String,
    val surahNumber: Int = 0,
    val ayahNumber: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "fasting_records")
data class FastingEntity(
    @PrimaryKey val dateString: String,
    val hijriDay: Int,
    val isFasted: Boolean,
    val note: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "last_read")
data class LastReadEntity(
    @PrimaryKey val id: Int = 1,
    val surahNumber: Int,
    val surahNameEn: String,
    val surahNameBn: String,
    val ayahNumber: Int,
    val timestamp: Long = System.currentTimeMillis()
)
