package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.PreferencesManager
import com.example.data.local.entities.FastingEntity
import com.example.prayer.PrayerTimeCalculator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class RamadanViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val fastingDao = db.fastingDao()
    private val prefs = PreferencesManager(application)

    val fastingList: StateFlow<List<FastingEntity>> = fastingDao.getAllFasting()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Daily goals state
    private val _quranPagesReadToday = MutableStateFlow(4)
    val quranPagesReadToday: StateFlow<Int> = _quranPagesReadToday.asStateFlow()

    private val _quranPageGoal = MutableStateFlow(20) // 1 Juz = 20 pages
    val quranPageGoal: StateFlow<Int> = _quranPageGoal.asStateFlow()

    private val _dhikrCountToday = MutableStateFlow(250)
    val dhikrCountToday: StateFlow<Int> = _dhikrCountToday.asStateFlow()

    private val _dhikrGoal = MutableStateFlow(500)
    val dhikrGoal: StateFlow<Int> = _dhikrGoal.asStateFlow()

    // Countdown details
    private val _suhoorTimeFormatted = MutableStateFlow("04:35 AM")
    val suhoorTimeFormatted: StateFlow<String> = _suhoorTimeFormatted.asStateFlow()

    private val _iftarTimeFormatted = MutableStateFlow("06:12 PM")
    val iftarTimeFormatted: StateFlow<String> = _iftarTimeFormatted.asStateFlow()

    private val _isFastingNow = MutableStateFlow(true)
    val isFastingNow: StateFlow<Boolean> = _isFastingNow.asStateFlow()

    private val _countdownToTarget = MutableStateFlow("00:00:00")
    val countdownToTarget: StateFlow<String> = _countdownToTarget.asStateFlow()

    private val _targetName = MutableStateFlow("Iftar")
    val targetName: StateFlow<String> = _targetName.asStateFlow()

    private var tickerJob: Job? = null

    init {
        startRamadanTicker()
    }

    private fun startRamadanTicker() {
        tickerJob?.cancel()
        tickerJob = viewModelScope.launch {
            while (isActive) {
                calculateTimes()
                delay(1000)
            }
        }
    }

    private fun calculateTimes() {
        val loc = prefs.userLocation.value
        val calc = prefs.calculationMethod.value
        val asr = prefs.asrMethod.value

        val cal = Calendar.getInstance()
        val tz = java.util.TimeZone.getDefault()
        val tzOffset = tz.getOffset(cal.timeInMillis) / 3600000.0

        val times = PrayerTimeCalculator.calculateTimes(cal, loc.latitude, loc.longitude, calc, asr, tzOffset)

        fun toMillis(hours: Double): Long {
            val h = hours.toInt()
            val m = ((hours - h) * 60).toInt()
            val c = Calendar.getInstance()
            c.set(Calendar.HOUR_OF_DAY, h.coerceIn(0, 23))
            c.set(Calendar.MINUTE, m.coerceIn(0, 59))
            c.set(Calendar.SECOND, 0)
            return c.timeInMillis
        }

        // Suhoor ends at Fajr (or Fajr - 10 mins Imsak)
        val suhoorMillis = toMillis(times.fajrHours)
        val iftarMillis = toMillis(times.maghribHours)

        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        _suhoorTimeFormatted.value = sdf.format(Date(suhoorMillis))
        _iftarTimeFormatted.value = sdf.format(Date(iftarMillis))

        val now = System.currentTimeMillis()
        if (now in suhoorMillis..iftarMillis) {
            _isFastingNow.value = true
            _targetName.value = "Iftar"
            val diffSecs = ((iftarMillis - now) / 1000).coerceAtLeast(0)
            val h = diffSecs / 3600
            val m = (diffSecs % 3600) / 60
            val s = diffSecs % 60
            _countdownToTarget.value = String.format(Locale.getDefault(), "%02d:%02d:%02d", h, m, s)
        } else {
            _isFastingNow.value = false
            _targetName.value = "Suhoor"
            val nextSuhoorMillis = if (now > iftarMillis) suhoorMillis + 24 * 3600 * 1000 else suhoorMillis
            val diffSecs = ((nextSuhoorMillis - now) / 1000).coerceAtLeast(0)
            val h = diffSecs / 3600
            val m = (diffSecs % 3600) / 60
            val s = diffSecs % 60
            _countdownToTarget.value = String.format(Locale.getDefault(), "%02d:%02d:%02d", h, m, s)
        }
    }

    fun toggleFastingDay(dayIndex: Int, dateStr: String, currentFasted: Boolean) {
        viewModelScope.launch {
            fastingDao.insertOrUpdate(
                FastingEntity(
                    dateString = dateStr,
                    hijriDay = dayIndex,
                    isFasted = !currentFasted
                )
            )
        }
    }

    fun incrementQuranPage() {
        if (_quranPagesReadToday.value < _quranPageGoal.value) {
            _quranPagesReadToday.value += 1
        }
    }

    fun incrementDhikrProgress(amount: Int = 10) {
        _dhikrCountToday.value += amount
    }

    override fun onCleared() {
        super.onCleared()
        tickerJob?.cancel()
    }
}
