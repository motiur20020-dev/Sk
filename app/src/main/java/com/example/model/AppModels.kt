package com.example.model

data class DuaCategory(
    val id: String,
    val titleEn: String,
    val titleBn: String,
    val titleAr: String,
    val iconName: String,
    val count: Int
)

data class DuaItem(
    val id: String,
    val categoryId: String,
    val titleEn: String,
    val titleBn: String,
    val arabicText: String,
    val transliterationEn: String,
    val transliterationBn: String,
    val meaningEn: String,
    val meaningBn: String,
    val reference: String,
    val recommendedCount: Int = 1,
    val benefitEn: String = "",
    val benefitBn: String = "",
    val isFavorite: Boolean = false
)

data class DhikrPreset(
    val id: String,
    val arabicText: String,
    val transliterationEn: String,
    val transliterationBn: String,
    val meaningEn: String,
    val meaningBn: String,
    val defaultTarget: Int = 33
)

data class IslamicHoliday(
    val nameEn: String,
    val nameBn: String,
    val hijriDay: Int,
    val hijriMonth: Int,
    val gregorianApprox: String,
    val descriptionEn: String,
    val descriptionBn: String,
    val isMajor: Boolean = false
)

enum class AppLanguage(val code: String, val labelEn: String, val labelBn: String) {
    ENGLISH("en", "English", "ইংরেজি"),
    BENGALI("bn", "Bengali", "বাংলা")
}

enum class AppThemeMode(val labelEn: String, val labelBn: String) {
    SYSTEM("System Default", "সিস্টেম ডিফল্ট"),
    LIGHT("Light", "লাইট"),
    DARK("Dark", "ডার্ক")
}
