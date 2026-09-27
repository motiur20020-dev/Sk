package com.example.prayer

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

object QiblaCalculator {
    // Kaaba coordinates in Mecca
    const val KAABA_LATITUDE = 21.4224779
    const val KAABA_LONGITUDE = 39.8261818

    /**
     * Calculates the Qibla azimuth direction from user's latitude and longitude in degrees (0..360, where 0 is North).
     */
    fun calculateQiblaBearing(userLat: Double, userLng: Double): Float {
        val lat1 = Math.toRadians(userLat)
        val lat2 = Math.toRadians(KAABA_LATITUDE)
        val deltaLng = Math.toRadians(KAABA_LONGITUDE - userLng)

        val y = sin(deltaLng)
        val x = cos(lat1) * tan(lat2) - sin(lat1) * cos(deltaLng)

        val bearingRad = atan2(y, x)
        val bearingDeg = Math.toDegrees(bearingRad)
        val normalized = (bearingDeg + 360.0) % 360.0
        return normalized.toFloat()
    }

    /**
     * Calculates distance to Kaaba in kilometers using Haversine formula
     */
    fun calculateDistanceToKaabaKm(userLat: Double, userLng: Double): Int {
        val earthRadiusKm = 6371.0
        val dLat = Math.toRadians(KAABA_LATITUDE - userLat)
        val dLng = Math.toRadians(KAABA_LONGITUDE - userLng)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(userLat)) * cos(Math.toRadians(KAABA_LATITUDE)) *
                sin(dLng / 2) * sin(dLng / 2)
        val c = 2 * atan2(kotlin.math.sqrt(a), kotlin.math.sqrt(1 - a))
        return (earthRadiusKm * c).toInt()
    }
}
