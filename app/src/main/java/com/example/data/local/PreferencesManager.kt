package com.example.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.model.AppLanguage
import com.example.model.AppThemeMode
import com.example.model.AsrMethod
import com.example.model.CalculationMethod
import com.example.model.UserLocation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("noor_muslim_prefs", Context.MODE_PRIVATE)

    private val _language = MutableStateFlow(getSavedLanguage())
    val language: StateFlow<AppLanguage> = _language.asStateFlow()

    private val _themeMode = MutableStateFlow(getSavedThemeMode())
    val themeMode: StateFlow<AppThemeMode> = _themeMode.asStateFlow()

    private val _calculationMethod = MutableStateFlow(getSavedCalculationMethod())
    val calculationMethod: StateFlow<CalculationMethod> = _calculationMethod.asStateFlow()

    private val _asrMethod = MutableStateFlow(getSavedAsrMethod())
    val asrMethod: StateFlow<AsrMethod> = _asrMethod.asStateFlow()

    private val _userLocation = MutableStateFlow(getSavedLocation())
    val userLocation: StateFlow<UserLocation> = _userLocation.asStateFlow()

    private val _quranFontSizeSp = MutableStateFlow(prefs.getInt("quran_font_size", 24))
    val quranFontSizeSp: StateFlow<Int> = _quranFontSizeSp.asStateFlow()

    private val _tasbihVibrate = MutableStateFlow(prefs.getBoolean("tasbih_vibrate", true))
    val tasbihVibrate: StateFlow<Boolean> = _tasbihVibrate.asStateFlow()

    private val _tasbihSound = MutableStateFlow(prefs.getBoolean("tasbih_sound", false))
    val tasbihSound: StateFlow<Boolean> = _tasbihSound.asStateFlow()

    // Prayer notifications
    private val _masterNotificationEnabled = MutableStateFlow(prefs.getBoolean("master_notifications", true))
    val masterNotificationEnabled: StateFlow<Boolean> = _masterNotificationEnabled.asStateFlow()

    private val _fajrNotif = MutableStateFlow(prefs.getBoolean("notif_fajr", true))
    val fajrNotif: StateFlow<Boolean> = _fajrNotif.asStateFlow()

    private val _dhuhrNotif = MutableStateFlow(prefs.getBoolean("notif_dhuhr", true))
    val dhuhrNotif: StateFlow<Boolean> = _dhuhrNotif.asStateFlow()

    private val _asrNotif = MutableStateFlow(prefs.getBoolean("notif_asr", true))
    val asrNotif: StateFlow<Boolean> = _asrNotif.asStateFlow()

    private val _maghribNotif = MutableStateFlow(prefs.getBoolean("notif_maghrib", true))
    val maghribNotif: StateFlow<Boolean> = _maghribNotif.asStateFlow()

    private val _ishaNotif = MutableStateFlow(prefs.getBoolean("notif_isha", true))
    val ishaNotif: StateFlow<Boolean> = _ishaNotif.asStateFlow()

    // Adhan audio enabled
    private val _masterAdhanEnabled = MutableStateFlow(prefs.getBoolean("master_adhan", true))
    val masterAdhanEnabled: StateFlow<Boolean> = _masterAdhanEnabled.asStateFlow()

    // Reminders
    private val _morningAzkarReminder = MutableStateFlow(prefs.getBoolean("reminder_morning_azkar", true))
    val morningAzkarReminder: StateFlow<Boolean> = _morningAzkarReminder.asStateFlow()

    private val _eveningAzkarReminder = MutableStateFlow(prefs.getBoolean("reminder_evening_azkar", true))
    val eveningAzkarReminder: StateFlow<Boolean> = _eveningAzkarReminder.asStateFlow()

    private val _quranReminder = MutableStateFlow(prefs.getBoolean("reminder_quran", true))
    val quranReminder: StateFlow<Boolean> = _quranReminder.asStateFlow()

    // Setters
    fun setLanguage(lang: AppLanguage) {
        prefs.edit().putString("language", lang.name).apply()
        _language.value = lang
    }

    fun setThemeMode(mode: AppThemeMode) {
        prefs.edit().putString("theme_mode", mode.name).apply()
        _themeMode.value = mode
    }

    fun setCalculationMethod(method: CalculationMethod) {
        prefs.edit().putString("calc_method", method.name).apply()
        _calculationMethod.value = method
    }

    fun setAsrMethod(method: AsrMethod) {
        prefs.edit().putString("asr_method", method.name).apply()
        _asrMethod.value = method
    }

    fun setLocation(location: UserLocation) {
        prefs.edit()
            .putFloat("lat", location.latitude.toFloat())
            .putFloat("lng", location.longitude.toFloat())
            .putString("city_en", location.cityNameEn)
            .putString("city_bn", location.cityNameBn)
            .putBoolean("is_gps", location.isGps)
            .apply()
        _userLocation.value = location
    }

    fun setQuranFontSize(sizeSp: Int) {
        prefs.edit().putInt("quran_font_size", sizeSp).apply()
        _quranFontSizeSp.value = sizeSp
    }

    fun setTasbihVibrate(enabled: Boolean) {
        prefs.edit().putBoolean("tasbih_vibrate", enabled).apply()
        _tasbihVibrate.value = enabled
    }

    fun setTasbihSound(enabled: Boolean) {
        prefs.edit().putBoolean("tasbih_sound", enabled).apply()
        _tasbihSound.value = enabled
    }

    fun setMasterNotifications(enabled: Boolean) {
        prefs.edit().putBoolean("master_notifications", enabled).apply()
        _masterNotificationEnabled.value = enabled
    }

    fun setPrayerNotification(prayerKey: String, enabled: Boolean) {
        prefs.edit().putBoolean("notif_$prayerKey", enabled).apply()
        when (prayerKey) {
            "fajr" -> _fajrNotif.value = enabled
            "dhuhr" -> _dhuhrNotif.value = enabled
            "asr" -> _asrNotif.value = enabled
            "maghrib" -> _maghribNotif.value = enabled
            "isha" -> _ishaNotif.value = enabled
        }
    }

    fun setMasterAdhan(enabled: Boolean) {
        prefs.edit().putBoolean("master_adhan", enabled).apply()
        _masterAdhanEnabled.value = enabled
    }

    fun setReminder(reminderKey: String, enabled: Boolean) {
        prefs.edit().putBoolean("reminder_$reminderKey", enabled).apply()
        when (reminderKey) {
            "morning_azkar" -> _morningAzkarReminder.value = enabled
            "evening_azkar" -> _eveningAzkarReminder.value = enabled
            "quran" -> _quranReminder.value = enabled
        }
    }

    private fun getSavedLanguage(): AppLanguage {
        val name = prefs.getString("language", AppLanguage.ENGLISH.name)
        return runCatching { AppLanguage.valueOf(name ?: AppLanguage.ENGLISH.name) }.getOrDefault(AppLanguage.ENGLISH)
    }

    private fun getSavedThemeMode(): AppThemeMode {
        val name = prefs.getString("theme_mode", AppThemeMode.SYSTEM.name)
        return runCatching { AppThemeMode.valueOf(name ?: AppThemeMode.SYSTEM.name) }.getOrDefault(AppThemeMode.SYSTEM)
    }

    private fun getSavedCalculationMethod(): CalculationMethod {
        val name = prefs.getString("calc_method", CalculationMethod.KARACHI.name)
        return runCatching { CalculationMethod.valueOf(name ?: CalculationMethod.KARACHI.name) }.getOrDefault(CalculationMethod.KARACHI)
    }

    private fun getSavedAsrMethod(): AsrMethod {
        val name = prefs.getString("asr_method", AsrMethod.HANAFI.name)
        return runCatching { AsrMethod.valueOf(name ?: AsrMethod.HANAFI.name) }.getOrDefault(AsrMethod.HANAFI)
    }

    private fun getSavedLocation(): UserLocation {
        val lat = prefs.getFloat("lat", 23.8103f).toDouble() // Default: Dhaka
        val lng = prefs.getFloat("lng", 90.4125f).toDouble()
        val cityEn = prefs.getString("city_en", "Dhaka") ?: "Dhaka"
        val cityBn = prefs.getString("city_bn", "ঢাকা") ?: "ঢাকা"
        val isGps = prefs.getBoolean("is_gps", false)
        return UserLocation(lat, lng, cityEn, cityBn, isGps)
    }
}
