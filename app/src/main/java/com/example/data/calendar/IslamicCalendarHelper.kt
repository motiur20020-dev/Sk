package com.example.data.calendar

import com.example.model.IslamicHoliday
import java.util.Calendar
import java.util.Date
import kotlin.math.floor

data class HijriDate(
    val day: Int,
    val month: Int, // 1 to 12
    val year: Int,
    val monthNameEn: String,
    val monthNameBn: String,
    val monthNameAr: String,
    val formattedEn: String,
    val formattedBn: String
)

object IslamicCalendarHelper {

    private val MONTH_NAMES_EN = listOf(
        "Muharram", "Safar", "Rabi' al-Awwal", "Rabi' al-Thani",
        "Jumada al-Awwal", "Jumada al-Thani", "Rajab", "Sha'ban",
        "Ramadan", "Shawwal", "Dhu al-Qi'dah", "Dhu al-Hijjah"
    )

    private val MONTH_NAMES_BN = listOf(
        "মুহররম", "সফর", "রবিউল আউয়াল", "রবিউস সানি",
        "জুমাদাল উলা", "জুমাদাস সানি", "রজব", "শা'বান",
        "রমযান", "শাওয়াল", "জিলক্বদ", "জিলহজ্জ"
    )

    private val MONTH_NAMES_AR = listOf(
        "محرّم", "صفر", "ربيع الأول", "ربيع الثاني",
        "جمادى الأولى", "جمادى الآخرة", "رجب", "شعبان",
        "رمضان", "شوّال", "ذو القعدة", "ذو الحجة"
    )

    val IMPORTANT_EVENTS = listOf(
        IslamicHoliday(
            nameEn = "Islamic New Year",
            nameBn = "ইসলামিক নববর্ষ (১লা মুহররম)",
            hijriDay = 1,
            hijriMonth = 1,
            gregorianApprox = "1st Muharram",
            descriptionEn = "Marks the migration (Hijrah) of Prophet Muhammad (PBUH) from Mecca to Medina.",
            descriptionBn = "রাসূলুল্লাহ (সাঃ)-এর মক্কা থেকে মদীনায় হিজরতের ঐতিহাসিক স্মারক।",
            isMajor = true
        ),
        IslamicHoliday(
            nameEn = "Day of Ashura",
            nameBn = "পবিত্র আশুরা (১০ই মুহররম)",
            hijriDay = 10,
            hijriMonth = 1,
            gregorianApprox = "10th Muharram",
            descriptionEn = "Fasting on this day expiates sins of the previous year. Historic day of deliverance of Prophet Musa (AS).",
            descriptionBn = "এই দিনের রোযা পূর্ববর্তী এক বছরের গুনাহ মাফ করে। হযরত মূসা (আঃ)-এর নাজাত লাভের দিন।",
            isMajor = true
        ),
        IslamicHoliday(
            nameEn = "Mawlid al-Nabi",
            nameBn = "ঈদে মিলাদুন্নবী (১২ই রবিউল আউয়াল)",
            hijriDay = 12,
            hijriMonth = 3,
            gregorianApprox = "12th Rabi' al-Awwal",
            descriptionEn = "Birth anniversary of the Final Messenger Prophet Muhammad (peace be upon him).",
            descriptionBn = "সর্বকালের সর্বশ্রেষ্ঠ মহামানব হযরত মুহাম্মদ (সাঃ)-এর জন্ম ও ওফাতের দিন।",
            isMajor = false
        ),
        IslamicHoliday(
            nameEn = "Shab-e-Miraj (Isra and Mi'raj)",
            nameBn = "শবে মেরাজ (২৭শে রজব)",
            hijriDay = 27,
            hijriMonth = 7,
            gregorianApprox = "27th Rajab",
            descriptionEn = "The miraculous night journey and heavenly ascension of Prophet Muhammad (PBUH) where five daily prayers were ordained.",
            descriptionBn = "রাসূলুল্লাহ (সাঃ)-এর ঊর্ধ্বাকাশ ভ্রমণ এবং পাঁচ ওয়াক্ত নামাযের উপহার প্রাপ্তির রাত।",
            isMajor = true
        ),
        IslamicHoliday(
            nameEn = "Shab-e-Barat (Mid-Sha'ban)",
            nameBn = "শবে বরাত (১৫ই শা'বান)",
            hijriDay = 15,
            hijriMonth = 8,
            gregorianApprox = "15th Sha'ban",
            descriptionEn = "Night of forgiveness, prayer, and divine mercy preceding the blessed month of Ramadan.",
            descriptionBn = "ক্ষমা, মুক্তি এবং রহমতের বরকতময় রাত যা রমযানের আগমন বার্তা নিয়ে আসে।",
            isMajor = true
        ),
        IslamicHoliday(
            nameEn = "First Day of Ramadan",
            nameBn = "পবিত্র মাহে রমযান শুরু (১লা রমযান)",
            hijriDay = 1,
            hijriMonth = 9,
            gregorianApprox = "1st Ramadan",
            descriptionEn = "The holy month of fasting, intense prayer, Quranic recitation, and self-purification.",
            descriptionBn = "রহমত, মাগফিরাত ও নাজাতের মাস। কুরআন নাযিলের পুণ্যময় মাস।",
            isMajor = true
        ),
        IslamicHoliday(
            nameEn = "Laylat al-Qadr (Night of Decree)",
            nameBn = "পবিত্র শবে কদর (২৭শে রমযান)",
            hijriDay = 27,
            hijriMonth = 9,
            gregorianApprox = "27th Ramadan (Odd night of last 10 days)",
            descriptionEn = "A night better than a thousand months. The Holy Quran was revealed in this blessed night.",
            descriptionBn = "হাজার মাসের চেয়েও উত্তম ও বরকতময় রাত। যে রাতে আল-কুরআন অবতীর্ণ হয়েছিল।",
            isMajor = true
        ),
        IslamicHoliday(
            nameEn = "Eid al-Fitr",
            nameBn = "পবিত্র ঈদুল ফিতর (১লা শাওয়াল)",
            hijriDay = 1,
            hijriMonth = 10,
            gregorianApprox = "1st Shawwal",
            descriptionEn = "Celebration marking the conclusion of the holy month of fasting with communal prayer and charity (Zakat al-Fitr).",
            descriptionBn = "মাসব্যাপী সিয়াম সাধনার পর আনন্দ ও কৃতজ্ঞতার মহিমান্বিত মুসলিম উৎসব।",
            isMajor = true
        ),
        IslamicHoliday(
            nameEn = "Day of Arafah",
            nameBn = "পবিত্র আরাফার দিন (৯ই জিলহজ্জ)",
            hijriDay = 9,
            hijriMonth = 12,
            gregorianApprox = "9th Dhu al-Hijjah",
            descriptionEn = "The pinnacle of Hajj pilgrimage. Fasting on this day expiates sins of two years.",
            descriptionBn = "হজ্জের প্রধান দিন। এদিন রোযা রাখা দুই বছরের সগীরা গুনাহের কাফফারা স্বরূপ।",
            isMajor = true
        ),
        IslamicHoliday(
            nameEn = "Eid al-Adha",
            nameBn = "পবিত্র ঈদুল আযহা (১০ই জিলহজ্জ)",
            hijriDay = 10,
            hijriMonth = 12,
            gregorianApprox = "10th Dhu al-Hijjah",
            descriptionEn = "Festival of Sacrifice commemorating Prophet Ibrahim's (AS) devotion to Allah. Days of Qurbani.",
            descriptionBn = "হযরত ইব্রাহীম (আঃ)-এর ত্যাগের স্মরণে কুরবানী ও তাকবীরের মহান উৎসব।",
            isMajor = true
        )
    )

    fun getHijriDate(date: Date, offsetDays: Int = 0): HijriDate {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.DAY_OF_YEAR, offsetDays)

        var y = cal.get(Calendar.YEAR)
        var m = cal.get(Calendar.MONTH) + 1 // 1..12
        val d = cal.get(Calendar.DAY_OF_MONTH)

        if (m <= 2) {
            y -= 1
            m += 12
        }

        val a = floor(y / 100.0)
        val b = 2 - a + floor(a / 4.0)
        val jd = floor(365.25 * (y + 4716)) + floor(30.6001 * (m + 1)) + d + b - 1524.5

        val l = jd - 1948440 + 10632
        val n = floor((l - 1) / 10631.0)
        val lRemaining = l - 10631 * n + 354
        val j = (floor((10985 - lRemaining) / 5316.0)) * (floor((50 * lRemaining) / 17719.0)) +
                (floor(lRemaining / 5670.0)) * (floor((43 * lRemaining) / 15238.0))
        val lFinal = lRemaining - (floor((30 - j) / 15.0)) * (floor((17719 * j) / 50.0)) -
                (floor(j / 16.0)) * (floor((15238 * j) / 43.0)) + 29
        val hMonth = floor((24 * lFinal) / 709.0).toInt().coerceIn(1, 12)
        val hDay = (lFinal - floor((709 * hMonth) / 24.0)).toInt().coerceIn(1, 30)
        val hYear = (30 * n + j - 30).toInt()

        val monthIdx = hMonth - 1
        val mEn = MONTH_NAMES_EN.getOrElse(monthIdx) { "Ramadan" }
        val mBn = MONTH_NAMES_BN.getOrElse(monthIdx) { "রমযান" }
        val mAr = MONTH_NAMES_AR.getOrElse(monthIdx) { "رمضان" }

        val fmtEn = "$hDay $mEn $hYear AH"
        val fmtBn = "$hDay $mBn $hYear হিজরি"

        return HijriDate(
            day = hDay,
            month = hMonth,
            year = hYear,
            monthNameEn = mEn,
            monthNameBn = mBn,
            monthNameAr = mAr,
            formattedEn = fmtEn,
            formattedBn = fmtBn
        )
    }

    fun getUpcomingEvent(currentHijri: HijriDate): IslamicHoliday? {
        val next = IMPORTANT_EVENTS.firstOrNull {
            it.hijriMonth > currentHijri.month || (it.hijriMonth == currentHijri.month && it.hijriDay >= currentHijri.day)
        }
        return next ?: IMPORTANT_EVENTS.firstOrNull()
    }
}
