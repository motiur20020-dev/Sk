package com.example.model

data class Surah(
    val number: Int,
    val nameArabic: String,
    val nameEnglish: String,
    val nameBengali: String,
    val meaningEnglish: String,
    val meaningBengali: String,
    val totalVerses: Int,
    val revelationType: RevelationType,
    val juzNumber: Int
)

enum class RevelationType(val en: String, val bn: String) {
    MECCAN("Meccan", "মাক্কী"),
    MEDINAN("Medinan", "মাদানী")
}

data class Ayah(
    val numberInSurah: Int,
    val surahNumber: Int,
    val textArabic: String,
    val textEnglish: String,
    val textBengali: String,
    val audioUrl: String? = null,
    val isBookmarked: Boolean = false
)

data class JuzInfo(
    val number: Int,
    val nameArabic: String,
    val nameEnglish: String,
    val nameBengali: String,
    val startSurahNumber: Int,
    val startAyahNumber: Int
)
