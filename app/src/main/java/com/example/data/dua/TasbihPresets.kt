package com.example.data.dua

import com.example.model.DhikrPreset

object TasbihPresets {
    val PRESETS = listOf(
        DhikrPreset(
            id = "subhanallah",
            arabicText = "سُبْحَانَ اللَّهِ",
            transliterationEn = "SubhanAllah",
            transliterationBn = "সুবহানাল্লাহ",
            meaningEn = "Glory be to Allah",
            meaningBn = "আল্লাহ পরম পবিত্র ও মহিমান্বিত",
            defaultTarget = 33
        ),
        DhikrPreset(
            id = "alhamdulillah",
            arabicText = "الْحَمْدُ لِلَّهِ",
            transliterationEn = "Alhamdulillah",
            transliterationBn = "আলহামদুলিল্লাহ",
            meaningEn = "All praise is due to Allah",
            meaningBn = "সমস্ত প্রশংসা একমাত্র আল্লাহর জন্য",
            defaultTarget = 33
        ),
        DhikrPreset(
            id = "allahuakbar",
            arabicText = "اللَّهُ أَكْبَرُ",
            transliterationEn = "Allahu Akbar",
            transliterationBn = "আল্লাহু আকবার",
            meaningEn = "Allah is the Greatest",
            meaningBn = "আল্লাহ সর্বশ্রেষ্ঠ",
            defaultTarget = 34
        ),
        DhikrPreset(
            id = "tahlil",
            arabicText = "لَا إِلَٰهَ إِلَّا اللَّهُ",
            transliterationEn = "La ilaha illallah",
            transliterationBn = "লা ইলাহা ইল্লাল্লাহ",
            meaningEn = "There is no deity worthy of worship except Allah",
            meaningBn = "আল্লাহ ব্যতীত সত্য কোনো উপাস্য নেই",
            defaultTarget = 100
        ),
        DhikrPreset(
            id = "istighfar",
            arabicText = "أَسْتَغْفِرُ اللَّهَ وَأَتُوبُ إِلَيْهِ",
            transliterationEn = "Astaghfirullaha wa atoobu ilayh",
            transliterationBn = "আস্তাগফিরুল্লাহা ওয়া আতূবু ইলাইহ",
            meaningEn = "I seek forgiveness from Allah and repent to Him",
            meaningBn = "আমি আল্লাহর নিকট ক্ষমা প্রার্থনা করছি এবং তাঁর দিকেই প্রত্যাবর্তন করছি",
            defaultTarget = 100
        ),
        DhikrPreset(
            id = "salawat",
            arabicText = "اللَّهُمَّ صَلِّ عَلَىٰ مُحَمَّدٍ وَعَلَىٰ آلِ مُحَمَّدٍ",
            transliterationEn = "Allahumma salli 'ala Muhammadin wa 'ala ali Muhammad",
            transliterationBn = "আল্লাহুম্মা সাল্লি আলা মুহাম্মাদিন ওয়া আলা আলি মুহাম্মাদ",
            meaningEn = "O Allah, send blessings upon Muhammad and upon the family of Muhammad",
            meaningBn = "হে আল্লাহ, মুহাম্মদ (সাঃ) এবং তাঁর পরিবারের ওপর শান্তি ও রহমত বর্ষণ করুন",
            defaultTarget = 100
        ),
        DhikrPreset(
            id = "hawqala",
            arabicText = "لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ الْعَلِيِّ الْعَظِيمِ",
            transliterationEn = "La hawla wa la quwwata illa billahil-'Aliyyil-'Azeem",
            transliterationBn = "লা হাওলা ওয়া লা কুওয়াতা ইল্লা বিল্লাহিল আলিয়্যিল আজিম",
            meaningEn = "There is no power nor might except with Allah, the Most High, the Most Great",
            meaningBn = "আল্লাহর সাহায্য ব্যতীত কোনো শক্তি নেই, কোনো পরাক্রম নেই; তিনি সর্বোচ্চ, সর্বমহান",
            defaultTarget = 100
        ),
        DhikrPreset(
            id = "hasbi",
            arabicText = "حَسْبُنَا اللَّهُ وَنِعْمَ الْوَكِيلُ",
            transliterationEn = "Hasbunallahu wa ni'mal wakeel",
            transliterationBn = "হাসবুনাল্লাহু ওয়া নি’মাল ওয়াকীল",
            meaningEn = "Allah is sufficient for us, and He is the best Disposer of affairs",
            meaningBn = "আল্লাহই আমাদের জন্য যথেষ্ট এবং তিনি উত্তম কর্মবিধায়ক",
            defaultTarget = 100
        )
    )
}
