package com.example.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.dua.TasbihPresets
import com.example.data.local.AppDatabase
import com.example.data.local.PreferencesManager
import com.example.data.local.entities.TasbihEntity
import com.example.model.DhikrPreset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TasbihViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val tasbihDao = db.tasbihDao()
    val preferencesManager = PreferencesManager(application)

    private val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = application.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
        vibratorManager?.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        application.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }

    val historyRecords: StateFlow<List<TasbihEntity>> = tasbihDao.getAllRecords()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val presets = TasbihPresets.PRESETS

    private val _selectedPreset = MutableStateFlow(presets.first())
    val selectedPreset: StateFlow<DhikrPreset> = _selectedPreset.asStateFlow()

    private val _currentCount = MutableStateFlow(0)
    val currentCount: StateFlow<Int> = _currentCount.asStateFlow()

    private val _targetCount = MutableStateFlow(33)
    val targetCount: StateFlow<Int> = _targetCount.asStateFlow()

    private val _totalCompletedSessions = MutableStateFlow(0)
    val totalCompletedSessions: StateFlow<Int> = _totalCompletedSessions.asStateFlow()

    fun selectPreset(preset: DhikrPreset) {
        _selectedPreset.value = preset
        _targetCount.value = preset.defaultTarget
        _currentCount.value = 0
    }

    fun setTargetCount(target: Int) {
        _targetCount.value = target
    }

    fun increment() {
        val target = _targetCount.value
        val newCount = _currentCount.value + 1
        _currentCount.value = newCount

        // Haptic feedback
        if (preferencesManager.tasbihVibrate.value) {
            triggerVibration(isTargetReached = target > 0 && newCount % target == 0)
        }

        // If target reached, save record to history automatically
        if (target > 0 && newCount % target == 0) {
            _totalCompletedSessions.value += 1
            saveRecord(target)
        }
    }

    fun resetCount() {
        if (_currentCount.value > 0) {
            saveRecord(_currentCount.value)
        }
        _currentCount.value = 0
    }

    private fun triggerVibration(isTargetReached: Boolean) {
        try {
            if (isTargetReached) {
                // Long pulse for target reached
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator?.vibrate(VibrationEffect.createOneShot(120, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator?.vibrate(120)
                }
            } else {
                // Short crisp click vibration
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    vibrator?.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator?.vibrate(VibrationEffect.createOneShot(25, 100))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator?.vibrate(25)
                }
            }
        } catch (_: Exception) {}
    }

    private fun saveRecord(count: Int) {
        viewModelScope.launch {
            val dateFmt = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
            tasbihDao.insertRecord(
                TasbihEntity(
                    dhikrName = _selectedPreset.value.transliterationEn,
                    count = count,
                    target = _targetCount.value,
                    dateFormatted = dateFmt.format(Date())
                )
            )
        }
    }

    fun clearAllHistory() {
        viewModelScope.launch {
            tasbihDao.clearAll()
        }
    }

    fun deleteHistoryRecord(id: Long) {
        viewModelScope.launch {
            tasbihDao.deleteById(id)
        }
    }
}
