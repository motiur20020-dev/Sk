package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.calendar.IslamicCalendarHelper
import com.example.data.dua.TasbihPresets
import com.example.data.quran.QuranRepository
import com.example.model.AsrMethod
import com.example.model.CalculationMethod
import com.example.model.UserLocation
import com.example.prayer.PrayerTimeCalculator
import com.example.prayer.QiblaCalculator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Calendar
import java.util.Date

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Noor Muslim", appName)
    }

    @Test
    fun `test prayer time calculations`() {
        val location = UserLocation(23.8103, 90.4125, "Dhaka", "ঢাকা")
        val schedule = PrayerTimeCalculator.buildPrayerSchedule(
            date = Date(),
            location = location,
            calcMethod = CalculationMethod.KARACHI,
            asrMethod = AsrMethod.HANAFI,
            hijriDateString = "15 Rabi' al-Awwal 1448 AH",
            cityName = "Dhaka"
        )

        assertNotNull(schedule)
        assertEquals(6, schedule.prayers.size) // Fajr, Sunrise, Dhuhr, Asr, Maghrib, Isha
        assertTrue(schedule.countdownSeconds >= 0)
    }

    @Test
    fun `test qibla bearing calculation`() {
        // Dhaka coordinates: ~276 degrees bearing to Mecca
        val bearing = QiblaCalculator.calculateQiblaBearing(23.8103, 90.4125)
        assertTrue("Bearing should be around 270-285 degrees", bearing in 265f..290f)

        // Distance should be positive
        val distance = QiblaCalculator.calculateDistanceToKaabaKm(23.8103, 90.4125)
        assertTrue("Distance should be around 5000km", distance in 4000..6000)
    }

    @Test
    fun `test quran data integrity`() {
        assertEquals("Quran should have 114 surahs", 114, QuranRepository.SURAH_LIST.size)
        val fatihahAyahs = QuranRepository.getAyahsForSurah(1)
        assertEquals("Al-Fatihah should have 7 ayahs", 7, fatihahAyahs.size)
    }

    @Test
    fun `test hijri date conversion`() {
        val hijri = IslamicCalendarHelper.getHijriDate(Date())
        assertTrue("Hijri year should be around 1447-1449", hijri.year in 1445..1455)
        assertTrue("Month should be between 1 and 12", hijri.month in 1..12)
        assertTrue("Day should be between 1 and 30", hijri.day in 1..30)
    }

    @Test
    fun `test tasbih presets loaded`() {
        assertTrue("Tasbih presets must not be empty", TasbihPresets.PRESETS.isNotEmpty())
    }
}
