package com.example.prayer

import com.example.model.AsrMethod
import com.example.model.CalculationMethod
import com.example.model.PrayerName
import com.example.model.PrayerSchedule
import com.example.model.PrayerTimeItem
import com.example.model.UserLocation
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

object PrayerTimeCalculator {

    data class CalculatedTimes(
        val fajrHours: Double,
        val sunriseHours: Double,
        val dhuhrHours: Double,
        val asrHours: Double,
        val sunsetHours: Double,
        val maghribHours: Double,
        val ishaHours: Double
    )

    fun calculateTimes(
        calendar: Calendar,
        latitude: Double,
        longitude: Double,
        calcMethod: CalculationMethod,
        asrMethod: AsrMethod,
        timeZoneOffsetHours: Double
    ): CalculatedTimes {
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        
        // Solar coordinates
        val b = 2.0 * Math.PI * (dayOfYear - 81) / 365.0
        val eqT = (9.87 * sin(2.0 * b) - 7.53 * cos(b) - 1.5 * sin(b)) / 60.0 // hours
        val declination = Math.toRadians(23.45 * sin(b)) // radians
        val latRad = Math.toRadians(latitude)

        // Solar noon (Dhuhr)
        val dhuhr = 12.0 + timeZoneOffsetHours - (longitude / 15.0) - eqT

        fun hourAngle(altitudeDegrees: Double): Double {
            val altRad = Math.toRadians(altitudeDegrees)
            val cosHA = (sin(altRad) - sin(latRad) * sin(declination)) / (cos(latRad) * cos(declination))
            val clamped = cosHA.coerceIn(-1.0, 1.0)
            return Math.toDegrees(acos(clamped)) / 15.0 // hours
        }

        // Sunrise & Sunset angle is typically -0.8333 degrees (accounting for sun disc & atmospheric refraction)
        val sunHA = hourAngle(-0.8333)
        val sunrise = dhuhr - sunHA
        val sunset = dhuhr + sunHA

        // Fajr & Isha
        val fajrHA = hourAngle(-calcMethod.fajrAngle)
        val fajr = dhuhr - fajrHA

        val ishaHA = hourAngle(-calcMethod.ishaAngle)
        val isha = dhuhr + ishaHA

        // Asr calculation
        val shadowFactor = asrMethod.shadowFactor
        val deltaLat = abs(latRad - declination)
        val asrAltRad = atan(1.0 / (shadowFactor + tan(deltaLat)))
        val cosAsrHA = (sin(asrAltRad) - sin(latRad) * sin(declination)) / (cos(latRad) * cos(declination))
        val asrHA = Math.toDegrees(acos(cosAsrHA.coerceIn(-1.0, 1.0))) / 15.0
        val asr = dhuhr + asrHA

        val maghrib = sunset + (2.0 / 60.0) // 2-3 minute buffer for safety

        return CalculatedTimes(
            fajrHours = fajr,
            sunriseHours = sunrise,
            dhuhrHours = dhuhr,
            asrHours = asr,
            sunsetHours = sunset,
            maghribHours = maghrib,
            ishaHours = isha
        )
    }

    fun buildPrayerSchedule(
        date: Date,
        location: UserLocation,
        calcMethod: CalculationMethod,
        asrMethod: AsrMethod,
        hijriDateString: String,
        cityName: String,
        notifMap: Map<PrayerName, Boolean> = emptyMap()
    ): PrayerSchedule {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val tz = TimeZone.getDefault()
        val tzOffsetHours = tz.getOffset(date.time) / 3600000.0

        val times = calculateTimes(calendar, location.latitude, location.longitude, calcMethod, asrMethod, tzOffsetHours)

        fun toMillis(hours: Double): Long {
            val h = hours.toInt()
            val m = ((hours - h) * 60).toInt()
            val cal = Calendar.getInstance()
            cal.time = date
            cal.set(Calendar.HOUR_OF_DAY, h.coerceIn(0, 23))
            cal.set(Calendar.MINUTE, m.coerceIn(0, 59))
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            return cal.timeInMillis
        }

        fun formatHours(hours: Double): String {
            val millis = toMillis(hours)
            val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
            return sdf.format(Date(millis))
        }

        val now = System.currentTimeMillis()

        val fajrMillis = toMillis(times.fajrHours)
        val sunriseMillis = toMillis(times.sunriseHours)
        val dhuhrMillis = toMillis(times.dhuhrHours)
        val asrMillis = toMillis(times.asrHours)
        val maghribMillis = toMillis(times.maghribHours)
        val ishaMillis = toMillis(times.ishaHours)

        val prayers = listOf(
            PrayerTimeItem(
                prayer = PrayerName.FAJR,
                nameEn = "Fajr",
                nameBn = "ফজর",
                nameAr = "الفجر",
                timeFormatted = formatHours(times.fajrHours),
                timestampMillis = fajrMillis,
                isPast = now > fajrMillis,
                isNotificationEnabled = notifMap[PrayerName.FAJR] ?: true
            ),
            PrayerTimeItem(
                prayer = PrayerName.SUNRISE,
                nameEn = "Sunrise",
                nameBn = "সূর্যোদয়",
                nameAr = "الشروق",
                timeFormatted = formatHours(times.sunriseHours),
                timestampMillis = sunriseMillis,
                isPast = now > sunriseMillis,
                isNotificationEnabled = false
            ),
            PrayerTimeItem(
                prayer = PrayerName.DHUHR,
                nameEn = "Dhuhr",
                nameBn = "যোহর",
                nameAr = "الظهر",
                timeFormatted = formatHours(times.dhuhrHours),
                timestampMillis = dhuhrMillis,
                isPast = now > dhuhrMillis,
                isNotificationEnabled = notifMap[PrayerName.DHUHR] ?: true
            ),
            PrayerTimeItem(
                prayer = PrayerName.ASR,
                nameEn = "Asr",
                nameBn = "আসর",
                nameAr = "العصر",
                timeFormatted = formatHours(times.asrHours),
                timestampMillis = asrMillis,
                isPast = now > asrMillis,
                isNotificationEnabled = notifMap[PrayerName.ASR] ?: true
            ),
            PrayerTimeItem(
                prayer = PrayerName.MAGHRIB,
                nameEn = "Maghrib",
                nameBn = "মাগরিব",
                nameAr = "المغرب",
                timeFormatted = formatHours(times.maghribHours),
                timestampMillis = maghribMillis,
                isPast = now > maghribMillis,
                isNotificationEnabled = notifMap[PrayerName.MAGHRIB] ?: true
            ),
            PrayerTimeItem(
                prayer = PrayerName.ISHA,
                nameEn = "Isha",
                nameBn = "ইশা",
                nameAr = "العشاء",
                timeFormatted = formatHours(times.ishaHours),
                timestampMillis = ishaMillis,
                isPast = now > ishaMillis,
                isNotificationEnabled = notifMap[PrayerName.ISHA] ?: true
            )
        )

        // Determine next prayer among the five daily prayers (excluding sunrise)
        val dailyPrayers = prayers.filter { it.prayer != PrayerName.SUNRISE }
        var next = dailyPrayers.firstOrNull { it.timestampMillis > now }
        var countdownSeconds = 0L

        if (next != null) {
            countdownSeconds = ((next.timestampMillis - now) / 1000).coerceAtLeast(0)
        } else {
            // Next is tomorrow's Fajr
            val tomorrowFajrMillis = fajrMillis + 24 * 3600 * 1000
            countdownSeconds = ((tomorrowFajrMillis - now) / 1000).coerceAtLeast(0)
            next = prayers.first().copy(timestampMillis = tomorrowFajrMillis, isNext = true)
        }

        val updatedPrayers = prayers.map {
            if (it.prayer == next.prayer) it.copy(isNext = true) else it
        }

        val hoursLeft = countdownSeconds / 3600
        val minsLeft = (countdownSeconds % 3600) / 60
        val secsLeft = countdownSeconds % 60
        val countdownStr = String.format(Locale.getDefault(), "%02d:%02d:%02d", hoursLeft, minsLeft, secsLeft)

        val dateFmt = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())

        return PrayerSchedule(
            gregorianDate = dateFmt.format(date),
            hijriDate = hijriDateString,
            locationName = cityName,
            prayers = updatedPrayers,
            nextPrayer = next,
            countdownFormatted = countdownStr,
            countdownSeconds = countdownSeconds,
            sunriseTime = formatHours(times.sunriseHours),
            sunsetTime = formatHours(times.sunsetHours)
        )
    }
}
