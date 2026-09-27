package com.example.model

enum class PrayerName(val id: String) {
    FAJR("fajr"),
    SUNRISE("sunrise"),
    DHUHR("dhuhr"),
    ASR("asr"),
    MAGHRIB("maghrib"),
    ISHA("isha")
}

data class PrayerTimeItem(
    val prayer: PrayerName,
    val nameEn: String,
    val nameBn: String,
    val nameAr: String,
    val timeFormatted: String, // e.g. "05:12 AM"
    val timestampMillis: Long,
    val isNext: Boolean = false,
    val isPast: Boolean = false,
    val isNotificationEnabled: Boolean = true,
    val isAdhanEnabled: Boolean = true
)

data class PrayerSchedule(
    val gregorianDate: String,
    val hijriDate: String,
    val locationName: String,
    val prayers: List<PrayerTimeItem>,
    val nextPrayer: PrayerTimeItem?,
    val countdownFormatted: String,
    val countdownSeconds: Long,
    val sunriseTime: String,
    val sunsetTime: String
)

enum class CalculationMethod(val displayName: String, val fajrAngle: Double, val ishaAngle: Double) {
    MUSLIM_WORLD_LEAGUE("Muslim World League", 18.0, 17.0),
    ISNA("Islamic Society of North America", 15.0, 15.0),
    EGYPT("Egyptian General Authority of Survey", 19.5, 17.5),
    UMM_AL_QURA("Umm al-Qura University, Makkah", 18.5, 19.0),
    KARACHI("University of Islamic Sciences, Karachi", 18.0, 18.0)
}

enum class AsrMethod(val displayNameEn: String, val displayNameBn: String, val shadowFactor: Double) {
    STANDARD("Shafi, Maliki, Hanbali (Standard)", "শাফেঈ, মালেকী, হাম্বলী (সাধারণ)", 1.0),
    HANAFI("Hanafi (Double Shadow)", "হানাফী (দ্বিগুণ ছায়া)", 2.0)
}

data class UserLocation(
    val latitude: Double,
    val longitude: Double,
    val cityNameEn: String,
    val cityNameBn: String,
    val isGps: Boolean = true
)
