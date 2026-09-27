package com.example.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.abs

class CompassSensorManager(context: Context) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
    private val rotationSensor: Sensor? = sensorManager?.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    private val accelerometer: Sensor? = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val magnetometer: Sensor? = sensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    private val _azimuth = MutableStateFlow(0f)
    val azimuth: StateFlow<Float> = _azimuth.asStateFlow()

    private val _sensorAvailable = MutableStateFlow(rotationSensor != null || (accelerometer != null && magnetometer != null))
    val sensorAvailable: StateFlow<Boolean> = _sensorAvailable.asStateFlow()

    private val _accuracy = MutableStateFlow(SensorManager.SENSOR_STATUS_ACCURACY_HIGH)
    val accuracy: StateFlow<Int> = _accuracy.asStateFlow()

    private val gravity = FloatArray(3)
    private val geomagnetic = FloatArray(3)
    private val rotationMatrix = FloatArray(9)
    private val orientation = FloatArray(3)

    private var currentFilteredAzimuth = 0f
    private val alpha = 0.15f // Low-pass filter smoothing coefficient

    fun startListening() {
        val sm = sensorManager ?: return
        if (rotationSensor != null) {
            sm.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_UI)
        } else {
            accelerometer?.let { sm.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
            magnetometer?.let { sm.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
        }
    }

    fun stopListening() {
        sensorManager?.unregisterListener(this)
    }

    fun setManualAzimuth(degrees: Float) {
        _azimuth.value = (degrees % 360f + 360f) % 360f
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
            SensorManager.getOrientation(rotationMatrix, orientation)
            var deg = Math.toDegrees(orientation[0].toDouble()).toFloat()
            deg = (deg + 360f) % 360f
            updateSmoothAzimuth(deg)
        } else {
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                System.arraycopy(event.values, 0, gravity, 0, 3)
            } else if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                System.arraycopy(event.values, 0, geomagnetic, 0, 3)
            }
            if (SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geomagnetic)) {
                SensorManager.getOrientation(rotationMatrix, orientation)
                var deg = Math.toDegrees(orientation[0].toDouble()).toFloat()
                deg = (deg + 360f) % 360f
                updateSmoothAzimuth(deg)
            }
        }
    }

    private fun updateSmoothAzimuth(targetDeg: Float) {
        // Handle circular wrap-around (e.g., from 359 to 1)
        var diff = targetDeg - currentFilteredAzimuth
        while (diff < -180f) diff += 360f
        while (diff > 180f) diff -= 360f

        currentFilteredAzimuth = (currentFilteredAzimuth + alpha * diff + 360f) % 360f
        _azimuth.value = currentFilteredAzimuth
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        _accuracy.value = accuracy
    }
}
