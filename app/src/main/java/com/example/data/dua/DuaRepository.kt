package com.example.data.dua

import com.example.model.DuaCategory
import com.example.model.DuaItem

object DuaRepository {

    val CATEGORIES = listOf(
        DuaCategory("morning", "Morning Azkar", "সকালের যিকির ও দো‘আ", "أذكار الصباح", "wb_sunny", 6),
        DuaCategory("evening", "Evening Azkar", "সন্ধ্যার যিকির ও দো‘আ", "أذكار المساء", "nights_stay", 6),
        DuaCategory("after_prayer", "After Salah", "নামাযের পরের দো‘আ", "أذكار بعد الصلاة", "mosque", 5),
        DuaCategory("daily", "Daily Duas", "দৈনন্দিন জীবনের দো‘আ", "أدعية يومية", "schedule", 8),
        DuaCategory("sleep", "Sleep & Waking", "ঘুমানো ও জাগ্রত হওয়ার দো‘আ", "أذكار النوم", "bedtime", 4),
        DuaCategory("travel", "Travel & Journey", "সফর ও ভ্রমণের দো‘আ", "دعاء السفر", "flight_takeoff", 4),
        DuaCategory("protection", "Protection & Ruqyah", "বিপদমুক্তি ও সুরক্ষার দো‘আ", "أدعية الحفظ", "shield", 5),
        DuaCategory("ramadan", "Ramadan & Fasting", "রমযান ও রোযার দো‘আ", "أدعية رمضان", "brightness_3", 5)
    )

    val ALL_DUAS = listOf(
        // Morning Azkar
        DuaItem(
            id = "m1",
            categoryId = "morning",
            titleEn = "Morning Supplication for Protection",
            titleBn = "সকালে আল্লাহর নামে সুরক্ষার দো‘আ",
            arabicText = "بِسْمِ اللَّهِ الَّذِي لَا يَضُرُّ مَعَ اسْمِهِ شَيْءٌ فِي الْأَرْضِ وَلَا فِي السَّمَاءِ وَهُوَ السَّمِيعُ الْعَلِيمُ",
            transliterationEn = "Bismillahillazi la yadurru ma'asmihi shay'un fil-ardi wa la fis-sama'i wa Huwas-Sami'ul-'Aleem.",
            transliterationBn = "বিসমিল্লাহিল্লাজি লা ইয়াদুররু মাআসমিহী শাইউন ফিল আরদ্বি ওয়ালা ফিস সামা-ই, ওয়াহুয়াস সামীউল আলীম।",
            meaningEn = "In the name of Allah, with whose name nothing on earth or in heaven can cause harm, and He is the All-Hearing, the All-Knowing.",
            meaningBn = "আল্লাহর নামে, যাঁর নামের বরকতে আসমান ও জমিনের কোনো কিছুই কোনো ক্ষতি করতে পারে না। আর তিনি সর্বশ্রোতা, সর্বজ্ঞ।",
            reference = "Abu Dawood, Tirmidhi (Recite 3 times)",
            recommendedCount = 3,
            benefitEn = "Whoever recites it 3 times in the morning and evening, nothing shall harm them.",
            benefitBn = "যে ব্যক্তি সকাল ও সন্ধ্যায় ৩ বার পাঠ করবে, কোনো কিছুই তার ক্ষতি করতে পারবে না।"
        ),
        DuaItem(
            id = "m2",
            categoryId = "morning",
            titleEn = "Sayyidul Istighfar (Master of Forgiveness)",
            titleBn = "সাইয়েদুল ইস্তেগফার (ক্ষমা প্রার্থনার শ্রেষ্ঠ দো‘আ)",
            arabicText = "اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ، خَلَقْتَنِي وَأَنَا عَبْدُكَ، وَأَنَا عَلَى عَهْدِكَ وَوَعْدِكَ مَا اسْتَطَعْتُ، أَعُوذُ بِكَ مِنْ شَرِّ مَا صَنَعْتُ، أَبُوءُ لَكَ بِنِعْمَتِكَ عَلَيَّ، وَأَبُوءُ بِذَنْبِي فَاغْفِرْ لِي فَإِنَّهُ لَا يَغْفِرُ الذُّنُوبَ إِلَّا أَنْتَ",
            transliterationEn = "Allahumma Anta Rabbi la ilaha illa Anta, khalaqtani wa ana 'abduka, wa ana 'ala 'ahdika wa wa'dika mastata'tu, a'oodhu bika min sharri ma sana'tu, aboo'u laka bini'matika 'alayya, wa aboo'u bidhanbi faghfir li fa-innahu la yaghfirudh-dhunooba illa Anta.",
            transliterationBn = "আল্লাহুম্মা আনতা রব্বী লা ইলাহা ইল্লা আনতা, খালাক্বতানী ওয়া আনা আবদুকা, ওয়া আনা আলা আহ্দিকা ওয়া ওয়া'দিকা মাসতাত্বা'তু, আউযুবিকা মিন শাররি মা ছানা'তু, আবূউ লাকা বিনি'মাতিকা আলাইয়া, ওয়া আবূউ বিযানবী ফাগ্ফির লী, ফাইন্নাহূ লা ইয়াগফিরুয যুনূবা ইল্লা আনতা।",
            meaningEn = "O Allah, You are my Lord; there is no god but You. You created me and I am Your servant, and I abide by Your covenant and promise as best I can. I seek refuge in You from the evil of what I have done. I acknowledge Your favor upon me, and I acknowledge my sin, so forgive me, for none forgives sins except You.",
            meaningBn = "হে আল্লাহ! আপনি আমার রব, আপনি ছাড়া কোনো ইলাহ নেই। আপনি আমাকে সৃষ্টি করেছেন এবং আমি আপনার বান্দা। আমি আমার সাধ্যমতো আপনার অঙ্গীকার ও প্রতিশ্রুতির ওপর অটল আছি। আমি যা করেছি তার অনিষ্ট থেকে আপনার কাছে আশ্রয় চাই। আমার ওপর আপনার যে নেয়ামত রয়েছে তা স্বীকার করছি, আর আমার পাপও স্বীকার করছি। অতএব আমাকে ক্ষমা করে দিন; কারণ আপনি ছাড়া আর কেউ গুনাহ ক্ষমা করতে পারে না।",
            reference = "Sahih Bukhari",
            recommendedCount = 1,
            benefitEn = "Whoever says this during the day with firm faith, and dies before evening, will be among the people of Paradise.",
            benefitBn = "যে ব্যক্তি দৃঢ় বিশ্বাসের সাথে দিনে পাঠ করে এবং সন্ধ্যার পূর্বে মারা যায়, সে জান্নাতীদের অন্তর্ভুক্ত হবে।"
        ),
        DuaItem(
            id = "m3",
            categoryId = "morning",
            titleEn = "Morning Praising Allah",
            titleBn = "সকালে দিনের সূচনায় আল্লাহর শুকরিয়া",
            arabicText = "أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ لِلَّهِ، وَالْحَمْدُ لِلَّهِ، لَا إِلَهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ",
            transliterationEn = "Asbahna wa asbahal-mulku lillah, wal-hamdu lillah, la ilaha illallahu wahdahu la shareeka lah, lahul-mulku wa lahul-hamdu wa Huwa 'ala kulli shay'in Qadeer.",
            transliterationBn = "আসবাহনা ওয়া আসবাহাল মুলকু লিল্লাহ, ওয়াল হামদু লিল্লাহ, লা ইলাহা ইল্লাল্লাহু ওয়াহদাহু লা শারীকা লাহ, লাহুল মুলকু ওয়া লাহুল হামদু ওয়া হুয়া আলা কুল্লি শাইয়িন ক্বাদীর।",
            meaningEn = "We have entered the morning and the kingdom belongs to Allah, and all praise is for Allah. There is no deity except Allah alone, without partner; to Him belongs sovereignty and to Him belongs praise, and He has power over all things.",
            meaningBn = "আমরা সকালে উপনীত হয়েছি এবং সমগ্র রাজত্বও আল্লাহর জন্যই সকালে উপনীত হয়েছে। সমস্ত প্রশংসা আল্লাহর। আল্লাহ ব্যতীত কোনো সত্য উপাস্য নেই, তিনি একক, তাঁর কোনো অংশীদার নেই। রাজত্ব কেবল তাঁরই এবং প্রশংসা কেবল তাঁরই, আর তিনি সব কিছুর ওপর ক্ষমতাবান।",
            reference = "Sahih Muslim",
            recommendedCount = 1
        ),

        // Evening Azkar
        DuaItem(
            id = "e1",
            categoryId = "evening",
            titleEn = "Evening Refuge in Allah's Perfect Words",
            titleBn = "সন্ধ্যায় আল্লাহর পূর্ণাঙ্গ কালেমার দ্বারা আশ্রয়",
            arabicText = "أَعُوذُ بِكَلِمَاتِ اللَّهِ التَّامَّاتِ مِنْ شَرِّ مَا خَلَقَ",
            transliterationEn = "A'oodhu bikalimatillahit-tammati min sharri ma khalaq.",
            transliterationBn = "আউযু বিকালিমাতিল্লাহিত তাম্মা-তি মিন শাররি মা খালাক্ব।",
            meaningEn = "I seek refuge in the perfect words of Allah from the evil of what He has created.",
            meaningBn = "আমি আশ্রয় চাই আল্লাহর পরিপূর্ণ বাণীসমূহের উসিলায়, তিনি যা সৃষ্টি করেছেন তার সমস্ত অনিষ্ট হতে।",
            reference = "Sahih Muslim (Recite 3 times)",
            recommendedCount = 3,
            benefitEn = "No harm, sting or poisonous bite will afflict the reciter that evening.",
            benefitBn = "যে ব্যক্তি সন্ধ্যায় ৩ বার এটি পাঠ করবে, সে রাতে কোনো বিষাক্ত কীট বা অনিষ্ট তাকে ক্ষতি করতে পারবে না।"
        ),
        DuaItem(
            id = "e2",
            categoryId = "evening",
            titleEn = "Evening Kingdom Declaration",
            titleBn = "সন্ধ্যায় আল্লাহর সার্বভৌমত্বের ঘোষণা",
            arabicText = "أَمْسَيْنَا وَأَمْسَى الْمُلْكُ لِلَّهِ وَالْحَمْدُ لِلَّهِ لَا إِلَهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ",
            transliterationEn = "Amsayna wa amsal-mulku lillah wal-hamdu lillah la ilaha illallahu wahdahu la shareeka lah.",
            transliterationBn = "আমসাইনা ওয়া আমসাল মুলকু লিল্লাহ ওয়াল হামদু লিল্লাহ লা ইলাহা ইল্লাল্লাহু ওয়াহদাহু লা শারীকা লাহ।",
            meaningEn = "We have reached the evening and the dominion belongs to Allah; all praise is to Allah, none has the right to be worshipped except Allah alone, without partner.",
            meaningBn = "আমরা সন্ধ্যায় উপনীত হলাম এবং আল্লাহর রাজত্বও সন্ধ্যায় উপনীত হল। সমস্ত প্রশংসা আল্লাহর, আল্লাহ ব্যতীত কোনো উপাস্য নেই, তিনি এক ও লা-শারীক।",
            reference = "Sahih Muslim",
            recommendedCount = 1
        ),

        // After Salah
        DuaItem(
            id = "s1",
            categoryId = "after_prayer",
            titleEn = "Seeking Forgiveness & Peace after Salah",
            titleBn = "সালাম ফেরানোর পর ক্ষমা ও শান্তির দো‘আ",
            arabicText = "أَسْتَغْفِرُ اللَّهَ، أَسْتَغْفِرُ اللَّهَ، أَسْتَغْفِرُ اللَّهَ، اللَّهُمَّ أَنْتَ السَّلَامُ وَمِنْكَ السَّلَامُ، تَبَارَكْتَ يَا ذَا الْجَلَالِ وَالْإِكْرَامِ",
            transliterationEn = "Astaghfirullah, Astaghfirullah, Astaghfirullah. Allahumma Antas-Salamu wa minkas-salam, tabarakta ya Dhal-Jalali wal-Ikram.",
            transliterationBn = "আস্তাগফিরুল্লাহ, আস্তাগফিরুল্লাহ, আস্তাগফিরুল্লাহ। আল্লাহুম্মা আনতাস সালামু ওয়া মিনকাস সালাম, তাবারাকতা ইয়া যাল জালালি ওয়াল ইকরাম।",
            meaningEn = "I seek Allah's forgiveness (3x). O Allah, You are Peace and from You comes peace. Blessed are You, O Owner of Majesty and Honor.",
            meaningBn = "আমি আল্লাহর ক্ষমা প্রার্থনা করছি (৩ বার)। হে আল্লাহ! আপনিই শান্তিময় এবং আপনার নিকট থেকেই শান্তি আসে। বরকতময় আপনি, হে মহিমাময় ও মহানুভবতার মালিক।",
            reference = "Sahih Muslim",
            recommendedCount = 1
        ),
        DuaItem(
            id = "s2",
            categoryId = "after_prayer",
            titleEn = "Tasbih Fatimi after Prayer",
            titleBn = "নামাযের পর তাসবীহে ফাতেমী",
            arabicText = "سُبْحَانَ اللَّهِ (٣٣) ، الْحَمْدُ لِلَّهِ (٣٣) ، اللَّهُ أَكْبَرُ (٣٣) ، لَا إِلَهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ",
            transliterationEn = "SubhanAllah (33x), Alhamdulillah (33x), Allahu Akbar (33x), La ilaha illallahu wahdahu la shareeka lah, lahul-mulku wa lahul-hamdu wa Huwa 'ala kulli shay'in Qadeer.",
            transliterationBn = "সুবহানাল্লাহ (৩৩ বার), আলহামদুলিল্লাহ (৩৩ বার), আল্লাহু আকবার (৩৩ বার), লা ইলাহা ইল্লাল্লাহু ওয়াহদাহু লা শারীকা লাহ, লাহুল মুলকু ওয়া লাহুল হামদু ওয়া হুয়া আলা কুল্লি শাইয়িন ক্বাদীর।",
            meaningEn = "Glory be to Allah (33), All praise to Allah (33), Allah is the Greatest (33), followed by declaring the oneness of Allah.",
            meaningBn = "সুবহানাল্লাহ ৩৩ বার, আলহামদুলিল্লাহ ৩৩ বার, আল্লাহু আকবার ৩৩ বার এবং শততম পূর্ণ করতে কালিমা তাওহীদ পাঠ করা।",
            reference = "Sahih Muslim",
            recommendedCount = 33,
            benefitEn = "Whoever recites this after every prayer, their sins will be forgiven even if they were like the foam of the sea.",
            benefitBn = "যে ব্যক্তি প্রতি নামাযের পর এটি পাঠ করবে, তার পাপরাশি সমুদ্রের ফেনার মতো হলেও ক্ষমা করে দেওয়া হবে।"
        ),

        // Sleep
        DuaItem(
            id = "sl1",
            categoryId = "sleep",
            titleEn = "Dua Before Sleeping",
            titleBn = "ঘুমানোর পূর্বে পঠিত দো‘আ",
            arabicText = "اللَّهُمَّ بِاسْمِكَ أَمُوتُ وَأَحْيَا",
            transliterationEn = "Allahumma bismika amootu wa ahya.",
            transliterationBn = "আল্লাহুম্মা বিসমিকা আমূতু ওয়া আহ্ইয়া।",
            meaningEn = "O Allah, in Your name I die and I live.",
            meaningBn = "হে আল্লাহ! আপনারই নামে আমি মৃত্যুবরণ করি (ঘুমাই) এবং আপনারই নামে জীবিত হই (জাগ্রত হই)।",
            reference = "Sahih Bukhari",
            recommendedCount = 1
        ),
        DuaItem(
            id = "sl2",
            categoryId = "sleep",
            titleEn = "Dua Upon Waking Up",
            titleBn = "ঘুম থেকে জাগ্রত হওয়ার দো‘আ",
            arabicText = "الْحَمْدُ لِلَّهِ الَّذِي أَحْيَانَا بَعْدَ مَا أَمَاتَنَا وَإِلَيْهِ النُّشُورُ",
            transliterationEn = "Alhamdulillahi-lladhi ahyana ba'da ma amatana wa ilayhin-nushoor.",
            transliterationBn = "আলহামদু লিল্লাহিল্লাজি আহ্ইয়ানা বা’দা মা আমাতানা ওয়া ইলাইহিন নুশূর।",
            meaningEn = "All praise is for Allah who gave us life after having taken it from us, and unto Him is the resurrection.",
            meaningBn = "সমস্ত প্রশংসা সেই আল্লাহর জন্য যিনি আমাদেরকে মৃত্যুর (ঘুমের) পর পুনরায় জীবিত করলেন, আর তাঁর দিকেই আমাদের পুনরুত্থান।",
            reference = "Sahih Bukhari",
            recommendedCount = 1
        ),

        // Travel
        DuaItem(
            id = "tr1",
            categoryId = "travel",
            titleEn = "Dua for Boarding Vehicle / Journey",
            titleBn = "বাহনে আরোহণের দো‘আ",
            arabicText = "سُبْحَانَ الَّذِي سَخَّرَ لَنَا هَٰذَا وَمَا كُنَّا لَهُ مُقْرِنِينَ وَإِنَّا إِلَىٰ رَبِّنَا لَمُنقَلِبُونَ",
            transliterationEn = "Subhanallazi sakh-khara lana hadha wa ma kunna lahu muqrineen, wa inna ila Rabbina lamunqaliboon.",
            transliterationBn = "সুবহানাল্লাজি সাখখারা লানা হাযা ওয়ামা কুন্না লাহূ মুকরিনীন, ওয়া ইন্না ইলা রব্বিনা লামুনক্বলিবূন।",
            meaningEn = "Glory to Him who has brought this under our control, though we were unable of ourselves to subdue it, and to our Lord we shall surely return.",
            meaningBn = "পবিত্র ও মহান সেই সত্তা, যিনি এটাকে আমাদের বশীভূত করে দিয়েছেন; অথচ আমরা একে বশীভূত করতে সক্ষম ছিলাম না। আর নিশ্চয় আমরা আমাদের রবের দিকেই প্রত্যাবর্তনকারী।",
            reference = "Surah Az-Zukhruf 13-14 / Muslim",
            recommendedCount = 1
        ),

        // Ramadan / Fasting
        DuaItem(
            id = "rm1",
            categoryId = "ramadan",
            titleEn = "Dua for Breaking Fast (Iftar)",
            titleBn = "ইফতারের দো‘আ",
            arabicText = "ذَهَبَ الظَّمَأُ وَابْتَلَّتِ الْعُرُوقُ وَثَبَتَ الْأَجْرُ إِنْ شَاءَ اللَّهُ",
            transliterationEn = "Dhahaba adh-dhama'u wabtallatil-'urooqu wa thabatal-ajru in sha Allah.",
            transliterationBn = "যাহাবায যমউ ওয়াবতাল্লাতিল উরূক্বু ওয়া ছাবাতাল আজরু ইনশাআল্লাহ।",
            meaningEn = "The thirst has gone, the veins are moistened, and the reward is confirmed, if Allah wills.",
            meaningBn = "পিপাসা নিবারিত হয়েছে, শিরা-উপশিরা সিক্ত হয়েছে এবং ইনশাআল্লাহ প্রতিদানও নির্ধারিত হয়ে গেছে।",
            reference = "Abu Dawood",
            recommendedCount = 1
        ),
        DuaItem(
            id = "rm2",
            categoryId = "ramadan",
            titleEn = "Dua for Laylat al-Qadr",
            titleBn = "শবে কদরের দো‘আ",
            arabicText = "اللَّهُمَّ إِنَّكَ عَفُوٌّ تُحِبُّ الْعَفْوَ فَاعْفُ عَنِّي",
            transliterationEn = "Allahumma innaka 'Afuwwun tuhibbul-'afwa fa'fu 'anni.",
            transliterationBn = "আল্লাহুম্মা ইন্নাকা আফুউন তুহিব্বুল আফওয়া ফা’ফু আন্নী।",
            meaningEn = "O Allah, You are Most Forgiving, and You love forgiveness; so forgive me.",
            meaningBn = "হে আল্লাহ! নিশ্চয় আপনি ক্ষমাশীল, আপনি ক্ষমা করতে ভালোবাসেন; অতএব আমাকে ক্ষমা করে দিন।",
            reference = "Tirmidhi, Ibn Majah",
            recommendedCount = 1
        )
    )
}
