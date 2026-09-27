package com.example.data.quran

import com.example.model.Ayah
import com.example.model.JuzInfo
import com.example.model.RevelationType
import com.example.model.Surah

object QuranRepository {

    // Audio stream base URL for Mishary Rashid Alafasy
    fun getSurahAudioUrl(surahNumber: Int): String {
        val padded = String.format("%03d", surahNumber)
        return "https://server8.mp3quran.net/afs/$padded.mp3"
    }

    fun getAyahAudioUrl(surahNumber: Int, ayahNumber: Int): String {
        val s = String.format("%03d", surahNumber)
        val a = String.format("%03d", ayahNumber)
        return "https://everyayah.com/data/Alafasy_128kbps/$s$a.mp3"
    }

    val JUZ_LIST: List<JuzInfo> = listOf(
        JuzInfo(1, "الم", "Alif Lam Meem", "আলিফ লাম মীম", 1, 1),
        JuzInfo(2, "سيقول", "Sayaqool", "সায়াকুল", 2, 142),
        JuzInfo(3, "تلك الرسل", "Tilka-r-Rusul", "তিলকার রুসুল", 2, 253),
        JuzInfo(4, "لن تنالوا", "Lan Tanaalu", "লান তানালু", 3, 92),
        JuzInfo(5, "والمحصنات", "Wal-Muhsanat", "ওয়াল মুহসানাত", 4, 24),
        JuzInfo(6, "لا يحب الله", "La Yuhibbullah", "লা ইউহিব্বুল্লাহ", 4, 148),
        JuzInfo(7, "وإذا سمعوا", "Wa Iza Sami'oo", "ওয়া ইযা সামিউ", 5, 83),
        JuzInfo(8, "ولو أننا", "Wa Lau Annana", "ওয়া লাও আন্নানা", 6, 111),
        JuzInfo(9, "قال الملأ", "Qalal Mala'u", "ক্বালা আল মালাউ", 7, 88),
        JuzInfo(10, "واعلموا", "Wa'lamoo", "ওয়া’লামু", 8, 41),
        JuzInfo(11, "يعتذرون", "Ya'taziroon", "ইয়া’তাযিরুন", 9, 93),
        JuzInfo(12, "وما من دابة", "Wa Ma Min Dabbatin", "ওয়া মা মিন দাব্বাহ", 11, 6),
        JuzInfo(13, "وما أبرئ", "Wa Ma Oobari'oo", "ওয়া মা উবাররিউ", 12, 53),
        JuzInfo(14, "ربما", "Rubama", "রুবা’মা", 15, 1),
        JuzInfo(15, "سبحان الذي", "Subhanallazi", "সুবহানাল্লাজি", 17, 1),
        JuzInfo(16, "قال ألم", "Qala Alam", "ক্বালা আলাম", 18, 75),
        JuzInfo(17, "اقترب للناس", "Iqtaraba Lin-Nasi", "ইক্বতারাবা লিন্নাস", 21, 1),
        JuzInfo(18, "قد أفلح", "Qad Aflaha", "ক্বাদ আফলাহা", 23, 1),
        JuzInfo(19, "وقال الذين", "Wa Qalal-Lazeena", "ওয়া ক্বালাল্লাযীনা", 25, 21),
        JuzInfo(20, "أمن خلق", "Amman Khalaq", "আম্মান খালাক্বা", 27, 56),
        JuzInfo(21, "اتل ما أوحي", "Utlu Ma Oohiya", "উতলু মা উহিয়া", 29, 46),
        JuzInfo(22, "ومن يقنت", "Wa Man Yaqnut", "ওয়া মান ইয়াক্বনুত", 33, 31),
        JuzInfo(23, "وما أنزلنا", "Wa Maliya", "ওয়া মালিয়া", 36, 28),
        JuzInfo(24, "فمن أظلم", "Fa Man Azlamu", "ফামান আযলামু", 39, 32),
        JuzInfo(25, "إليه يرد", "Ilayhi Yuraddu", "ইলাইহি ইউরাদ্দু", 41, 47),
        JuzInfo(26, "حم", "Ha'a Meem", "হা-মীম", 46, 1),
        JuzInfo(27, "قال فما خطبكم", "Qala Fama Khatbukum", "ক্বালা ফামা খাতবুকুম", 51, 31),
        JuzInfo(28, "قد سمع الله", "Qad Sami'a Allah", "ক্বাদ সামিয়া আল্লাহ", 58, 1),
        JuzInfo(29, "تبارك الذي", "Tabarakallazi", "তাবারাকাল্লাজি", 67, 1),
        JuzInfo(30, "عم يتساءلون", "Amma Yatasa'aloon", "আম্মা ইয়াতাসা’আলূন", 78, 1)
    )

    val SURAH_LIST: List<Surah> = listOf(
        Surah(1, "الفاتحة", "Al-Fatihah", "আল-ফাতিহা", "The Opening", "সূচনা", 7, RevelationType.MECCAN, 1),
        Surah(2, "البقرة", "Al-Baqarah", "আল-বাক্বারাহ", "The Cow", "বকনা-বাছুর", 286, RevelationType.MEDINAN, 1),
        Surah(3, "آل عمران", "Ali 'Imran", "আল-ইমরান", "Family of Imran", "ইমরানের পরিবার", 200, RevelationType.MEDINAN, 3),
        Surah(4, "النساء", "An-Nisa", "আন-নিসা", "The Women", "মহিলা", 176, RevelationType.MEDINAN, 4),
        Surah(5, "المائدة", "Al-Ma'idah", "আল-মায়িদাহ", "The Table Spread", "খাদ্য পরিবেশিত টেবিল", 120, RevelationType.MEDINAN, 6),
        Surah(6, "الأنعام", "Al-An'am", "আল-আনআম", "The Cattle", "গৃহপালিত পশু", 165, RevelationType.MECCAN, 7),
        Surah(7, "الأعراف", "Al-A'raf", "আল-আরাফ", "The Heights", "উঁচু স্থানসমূহ", 206, RevelationType.MECCAN, 8),
        Surah(8, "الأنفال", "Al-Anfal", "আল-আনফাল", "The Spoils of War", "যুদ্ধলব্ধ ধন-সম্পদ", 75, RevelationType.MEDINAN, 9),
        Surah(9, "التوبة", "At-Tawbah", "আত-তাওবাহ", "The Repentance", "অনুশোচনা", 129, RevelationType.MEDINAN, 10),
        Surah(10, "يونس", "Yunus", "ইউনুস", "Jonah", "ইউনুস (আঃ)", 109, RevelationType.MECCAN, 11),
        Surah(11, "هود", "Hud", "হুদ", "Hud", "হুদ (আঃ)", 123, RevelationType.MECCAN, 11),
        Surah(12, "يوسف", "Yusuf", "ইউসুফ", "Joseph", "ইউসুফ (আঃ)", 111, RevelationType.MECCAN, 12),
        Surah(13, "الرعد", "Ar-Ra'd", "আর-রাদ", "The Thunder", "বজ্রপাত", 43, RevelationType.MEDINAN, 13),
        Surah(14, "إبراهيم", "Ibrahim", "ইব্রাহীম", "Abraham", "ইব্রাহীম (আঃ)", 52, RevelationType.MECCAN, 13),
        Surah(15, "الحجر", "Al-Hijr", "আল-হিজর", "The Rocky Tract", "পাথুরে পাহাড়", 99, RevelationType.MECCAN, 14),
        Surah(16, "النحل", "An-Nahl", "আন-নাহল", "The Bee", "মৌমাছি", 128, RevelationType.MECCAN, 14),
        Surah(17, "الإسراء", "Al-Isra", "আল-ইসরা", "The Night Journey", "রজনী ভ্রমণ", 111, RevelationType.MECCAN, 15),
        Surah(18, "الكهف", "Al-Kahf", "আল-কাহফ", "The Cave", "গুহা", 110, RevelationType.MECCAN, 15),
        Surah(19, "مريم", "Maryam", "মারইয়াম", "Mary", "মারইয়াম (আঃ)", 98, RevelationType.MECCAN, 16),
        Surah(20, "طه", "Ta-Ha", "ত্বা-হা", "Ta-Ha", "ত্বা-হা", 135, RevelationType.MECCAN, 16),
        Surah(21, "الأنبياء", "Al-Anbiya", "আল-আম্বিয়া", "The Prophets", "নবীগণ", 112, RevelationType.MECCAN, 17),
        Surah(22, "الحج", "Al-Hajj", "আল-হজ্জ", "The Pilgrimage", "হজ্জ", 78, RevelationType.MEDINAN, 17),
        Surah(23, "المؤمنون", "Al-Mu'minun", "আল-মুমিনূন", "The Believers", "বিশ্বাসীগণ", 118, RevelationType.MECCAN, 18),
        Surah(24, "النور", "An-Nur", "আন-নূর", "The Light", "আলো", 64, RevelationType.MEDINAN, 18),
        Surah(25, "الفرقان", "Al-Furqan", "আল-ফুরক্বান", "The Criterion", "সত্য ও মিথ্যার পার্থক্যকারী", 77, RevelationType.MECCAN, 18),
        Surah(26, "الشعراء", "Ash-Shu'ara", "আশ-শুআরা", "The Poets", "কবিগণ", 227, RevelationType.MECCAN, 19),
        Surah(27, "النمل", "An-Naml", "আন-নামল", "The Ant", "পিপীলিকা", 93, RevelationType.MECCAN, 19),
        Surah(28, "القصص", "Al-Qasas", "আল-ক্বাসাছ", "The Stories", "কাহিনী", 88, RevelationType.MECCAN, 20),
        Surah(29, "العنكبوت", "Al-'Ankabut", "আল-আনকাবূত", "The Spider", "মাকড়সা", 69, RevelationType.MECCAN, 20),
        Surah(30, "الروم", "Ar-Rum", "আর-রূম", "The Romans", "রোমান জাতি", 60, RevelationType.MECCAN, 21),
        Surah(31, "لقمان", "Luqman", "লুক্বমান", "Luqman", "লুকমান (আঃ)", 34, RevelationType.MECCAN, 21),
        Surah(32, "السجدة", "As-Sajdah", "আস-সাজদাহ", "The Prostration", "সিজদা", 30, RevelationType.MECCAN, 21),
        Surah(33, "الأحزاب", "Al-Ahzab", "আল-আহযাব", "The Combined Forces", "জোটবদ্ধ দল", 73, RevelationType.MEDINAN, 21),
        Surah(34, "سبأ", "Saba", "সাবা", "Sheba", "সাবা জাতি", 54, RevelationType.MECCAN, 22),
        Surah(35, "فاطر", "Fatir", "ফাতির", "Originator", "সৃষ্টিকর্তা", 45, RevelationType.MECCAN, 22),
        Surah(36, "يس", "Ya-Sin", "ইয়াসীন", "Ya-Sin", "ইয়াসীন", 83, RevelationType.MECCAN, 22),
        Surah(37, "الصافات", "As-Saffat", "আস-সাফফাত", "Those who set the Ranks", "সারিবদ্ধভাবে দাঁড়ানো", 182, RevelationType.MECCAN, 23),
        Surah(38, "ص", "Sad", "সোয়াদ", "The Letter Sad", "সোয়াদ", 88, RevelationType.MECCAN, 23),
        Surah(39, "الزمر", "Az-Zumar", "আজ-জুমার", "The Troops", "দলবদ্ধ জনতা", 75, RevelationType.MECCAN, 23),
        Surah(40, "غافر", "Ghafir", "গাফির", "The Forgiver", "ক্ষমাশীল", 85, RevelationType.MECCAN, 24),
        Surah(41, "فصلت", "Fussilat", "ফুসসিলাত", "Explained in Detail", "সুস্পষ্ট বিবরণ", 54, RevelationType.MECCAN, 24),
        Surah(42, "الشورى", "Ash-Shura", "আশ-শুরা", "The Consultation", "পরামর্শ", 53, RevelationType.MECCAN, 25),
        Surah(43, "الزخرف", "Az-Zukhruf", "আজ-জুখরুফ", "The Ornaments of Gold", "সোনার অলঙ্কার", 89, RevelationType.MECCAN, 25),
        Surah(44, "الدخان", "Ad-Dukhan", "আদ-দুখান", "The Smoke", "ধোঁয়া", 59, RevelationType.MECCAN, 25),
        Surah(45, "الجاثية", "Al-Jathiyah", "আল-জাসিয়া", "The Crouching", "নতজানু", 37, RevelationType.MECCAN, 25),
        Surah(46, "الأحقاف", "Al-Ahqaf", "আল-আহক্বাফ", "The Wind-Curved Sandhills", "বালুর পাহাড়", 35, RevelationType.MECCAN, 26),
        Surah(47, "محمد", "Muhammad", "মুহাম্মদ", "Muhammad", "মুহাম্মদ (সাঃ)", 38, RevelationType.MEDINAN, 26),
        Surah(48, "الفتح", "Al-Fath", "আল-ফাতহ", "The Victory", "বিজয়", 29, RevelationType.MEDINAN, 26),
        Surah(49, "الحجرات", "Al-Hujurat", "আল-হুজুরাত", "The Rooms", "বাসগৃহসমূহ", 18, RevelationType.MEDINAN, 26),
        Surah(50, "ق", "Qaf", "ক্বাফ", "The Letter Qaf", "ক্বাফ", 45, RevelationType.MECCAN, 26),
        Surah(51, "الذاريات", "Adh-Dhariyat", "আয-যারিয়াত", "The Winnowing Winds", "বিক্ষিপ্তকারী বাতাস", 60, RevelationType.MECCAN, 26),
        Surah(52, "الطور", "At-Tur", "আত-তূর", "The Mount", "তূর পাহাড়", 49, RevelationType.MECCAN, 27),
        Surah(53, "النجم", "An-Najm", "আন-নাজম", "The Star", "তারা", 62, RevelationType.MECCAN, 27),
        Surah(54, "القمر", "Al-Qamar", "আল-ক্বামার", "The Moon", "চাঁদ", 55, RevelationType.MECCAN, 27),
        Surah(55, "الرحمن", "Ar-Rahman", "আর-রাহমান", "The Beneficent", "পরম করুণাময়", 78, RevelationType.MEDINAN, 27),
        Surah(56, "الواقعة", "Al-Waqi'ah", "আল-ওয়াক্বিয়া", "The Inevitable", "সুনিশ্চিত ঘটনা", 96, RevelationType.MECCAN, 27),
        Surah(57, "الحديد", "Al-Hadid", "আল-হাদীদ", "The Iron", "লোহা", 29, RevelationType.MEDINAN, 27),
        Surah(58, "المجادلة", "Al-Mujadila", "আল-মুজাদালাহ", "The Pleading Woman", "বিতর্ককারিণী", 22, RevelationType.MEDINAN, 28),
        Surah(59, "الحشر", "Al-Hashr", "আল-হাশর", "The Exile", "সমাবেশ / বিতাড়ন", 24, RevelationType.MEDINAN, 28),
        Surah(60, "الممتحنة", "Al-Mumtahanah", "আল-মুমতাহিনা", "She that is to be examined", "পরীক্ষিতা নারী", 13, RevelationType.MEDINAN, 28),
        Surah(61, "الصف", "As-Saff", "আস-সাফ", "The Ranks", "সারিবদ্ধ সৈন্য", 14, RevelationType.MEDINAN, 28),
        Surah(62, "الجمعة", "Al-Jumu'ah", "আল-জুমুআহ", "Friday", "শুক্রবার / জুমুআ", 11, RevelationType.MEDINAN, 28),
        Surah(63, "المنافقون", "Al-Munafiqun", "আল-মুনাফিকূন", "The Hypocrites", "কপট বিশ্বাসীগণ", 11, RevelationType.MEDINAN, 28),
        Surah(64, "التغابن", "At-Taghabun", "আত-তাগাবুন", "Mutual Disillusion", "লাভ-ক্ষতি", 18, RevelationType.MEDINAN, 28),
        Surah(65, "الطلاق", "At-Talaq", "আত-ত্বালাক্ব", "The Divorce", "তালাক", 12, RevelationType.MEDINAN, 28),
        Surah(66, "التحريم", "At-Tahrim", "আত-তাহরীম", "The Prohibition", "নিষিদ্ধকরণ", 12, RevelationType.MEDINAN, 28),
        Surah(67, "الملك", "Al-Mulk", "আল-মুলক", "The Sovereignty", "সার্বভৌম কর্তৃত্ব", 30, RevelationType.MECCAN, 29),
        Surah(68, "القلم", "Al-Qalam", "আল-ক্বলম", "The Pen", "কলম", 52, RevelationType.MECCAN, 29),
        Surah(69, "الحاقة", "Al-Haqqah", "আল-হাক্বক্বাহ", "The Inevitable Truth", "অমোঘ সত্য", 52, RevelationType.MECCAN, 29),
        Surah(70, "المعارج", "Al-Ma'arij", "আল-মাআরিজ", "The Ascending Stairways", "উন্নয়নের সোপান", 44, RevelationType.MECCAN, 29),
        Surah(71, "نوح", "Nuh", "নূহ", "Noah", "নূহ (আঃ)", 28, RevelationType.MECCAN, 29),
        Surah(72, "الجن", "Al-Jinn", "আল-জ্বিন", "The Jinn", "জিন সম্প্রদায়", 28, RevelationType.MECCAN, 29),
        Surah(73, "المزمل", "Al-Muzzammil", "আল-মুযযাম্মিল", "The Enshrouded One", "বস্ত্রাচ্ছাদনকারী", 20, RevelationType.MECCAN, 29),
        Surah(74, "المدثر", "Al-Muddaththir", "আল-মুদ্দাসসির", "The Cloaked One", "পোশাক পরিহিত", 56, RevelationType.MECCAN, 29),
        Surah(75, "القيامة", "Al-Qiyamah", "আল-ক্বিয়ামাহ", "The Resurrection", "কেয়ামত / পুনরুত্থান", 40, RevelationType.MECCAN, 29),
        Surah(76, "الإنسان", "Al-Insan", "আল-ইনসান", "Man", "মানবজাতি", 31, RevelationType.MEDINAN, 29),
        Surah(77, "المرسلات", "Al-Mursalat", "আল-মুরসালাত", "The Emissaries", "প্রেরিত বাতাস", 50, RevelationType.MECCAN, 29),
        Surah(78, "النبأ", "An-Naba", "আন-নাবা", "The Tidings", "মহাসংবাদ", 40, RevelationType.MECCAN, 30),
        Surah(79, "النازعات", "An-Nazi'at", "আন-নাযিআত", "Those who drag forth", "উৎপাটনকারী", 46, RevelationType.MECCAN, 30),
        Surah(80, "عبس", "Abasa", "আবাসা", "He Frowned", "তিনি ভ্রূকুটি করলেন", 42, RevelationType.MECCAN, 30),
        Surah(81, "التكوير", "At-Takwir", "আত-তাকভীর", "The Overthrowing", "সূর্য অন্ধকারাচ্ছন্ন হওয়া", 29, RevelationType.MECCAN, 30),
        Surah(82, "الانفطار", "Al-Infitar", "আল-ইনফিতার", "The Cleaving", "বিদীর্ণ হওয়া", 19, RevelationType.MECCAN, 30),
        Surah(83, "المطففين", "Al-Mutaffifin", "আল-মুতাফফিফীন", "The Defrauding", "প্রতারণাকারী", 36, RevelationType.MECCAN, 30),
        Surah(84, "الانشقاق", "Al-Inshiqaq", "আল-ইনশিক্বাক্ব", "The Splitting Open", "খণ্ড-বিখণ্ড হওয়া", 25, RevelationType.MECCAN, 30),
        Surah(85, "البروج", "Al-Buruj", "আল-বুরূজ", "The Mansions of the Stars", "নক্ষত্রপুঞ্জ", 22, RevelationType.MECCAN, 30),
        Surah(86, "الطارق", "At-Tariq", "আত-ত্বারিক্ব", "The Morning Star", "রাতের আগমনকারী", 17, RevelationType.MECCAN, 30),
        Surah(87, "الأعلى", "Al-A'la", "আল-আলা", "The Most High", "সর্বোচ্চ", 19, RevelationType.MECCAN, 30),
        Surah(88, "الغاشية", "Al-Ghashiyah", "আল-গাশিয়াহ", "The Overwhelming", "আচ্ছন্নকারী সংকট", 26, RevelationType.MECCAN, 30),
        Surah(89, "الفجر", "Al-Fajr", "আল-ফজর", "The Dawn", "ঊষা / ভোর", 30, RevelationType.MECCAN, 30),
        Surah(90, "البلد", "Al-Balad", "আল-বালাদ", "The City", "নগরী", 20, RevelationType.MECCAN, 30),
        Surah(91, "الشمس", "Ash-Shams", "আশ-শামস", "The Sun", "সূর্য", 15, RevelationType.MECCAN, 30),
        Surah(92, "الليل", "Al-Layl", "আল-লায়ল", "The Night", "রাত্রি", 21, RevelationType.MECCAN, 30),
        Surah(93, "الضحى", "Ad-Duha", "আদ-দুহা", "The Morning Hours", "পূর্বাহ্ণ", 11, RevelationType.MECCAN, 30),
        Surah(94, "الشرح", "Ash-Sharh", "আশ-শারহ", "The Relief", "বক্ষ প্রশস্তকরণ", 8, RevelationType.MECCAN, 30),
        Surah(95, "التين", "At-Tin", "আত-তীন", "The Fig", "ডুমুর", 8, RevelationType.MECCAN, 30),
        Surah(96, "العلق", "Al-'Alaq", "আল-আলাক্ব", "The Clot", "রক্তপিণ্ড", 19, RevelationType.MECCAN, 30),
        Surah(97, "القدر", "Al-Qadr", "আল-ক্বদর", "The Power / Decree", "মর্যাদাময় রাত", 5, RevelationType.MECCAN, 30),
        Surah(98, "البينة", "Al-Bayyinah", "আল-বায়্যিনাহ", "The Clear Proof", "সুস্পষ্ট প্রমাণ", 8, RevelationType.MEDINAN, 30),
        Surah(99, "الزلزلة", "Az-Zalzalah", "আজ-যালযালাহ", "The Earthquake", "ভূমিকম্প", 8, RevelationType.MEDINAN, 30),
        Surah(100, "العاديات", "Al-'Adiyat", "আল-আদিয়াত", "The Courser", "অভিযানকারী অশ্ব", 11, RevelationType.MECCAN, 30),
        Surah(101, "القارعة", "Al-Qari'ah", "আল-ক্বারিয়াহ", "The Calamity", "মহা বিপদ", 11, RevelationType.MECCAN, 30),
        Surah(102, "التكاثر", "At-Takathur", "আত-তাকাসুর", "The Rivalry in world increase", "প্রাচুর্যের প্রতিযোগিতা", 8, RevelationType.MECCAN, 30),
        Surah(103, "العصر", "Al-'Asr", "আল-আসর", "The Declining Day", "মহাকাল / সময়", 3, RevelationType.MECCAN, 30),
        Surah(104, "الهمزة", "Al-Humazah", "আল-হুমাযাহ", "The Traducer", "পরনিন্দাকারী", 9, RevelationType.MECCAN, 30),
        Surah(105, "الفيل", "Al-Fil", "আল-ফীল", "The Elephant", "হাতি", 5, RevelationType.MECCAN, 30),
        Surah(106, "قريش", "Quraysh", "কুরাইশ", "Quraysh", "কুরাইশ বংশ", 4, RevelationType.MECCAN, 30),
        Surah(107, "الماعون", "Al-Ma'un", "আল-মাউন", "The Small Kindnesses", "সাহায্য-সহায়তা", 7, RevelationType.MECCAN, 30),
        Surah(108, "الكوثر", "Al-Kawthar", "আল-কাউসার", "The Abundance", "প্রচুর প্রাচুর্য", 3, RevelationType.MECCAN, 30),
        Surah(109, "الكافرون", "Al-Kafirun", "আল-কাফিরূন", "The Disbelievers", "অবিশ্বাসীগণ", 6, RevelationType.MECCAN, 30),
        Surah(110, "النصر", "An-Nasr", "আন-নাসর", "The Divine Support", "সাহায্য", 3, RevelationType.MEDINAN, 30),
        Surah(111, "المسد", "Al-Masad", "আল-মাসাদ", "The Palm Fibre", "খেজুরের রশি", 5, RevelationType.MECCAN, 30),
        Surah(112, "الإخلاص", "Al-Ikhlas", "আল-ইখলাস", "The Sincerity", "একনিষ্ঠতা", 4, RevelationType.MECCAN, 30),
        Surah(113, "الفلق", "Al-Falaq", "আল-ফালাক্ব", "The Daybreak", "নিশিভোর", 5, RevelationType.MECCAN, 30),
        Surah(114, "الناس", "An-Nas", "আন-নাস", "Mankind", "মানবজাতি", 6, RevelationType.MECCAN, 30)
    )

    fun getAyahsForSurah(surahNumber: Int): List<Ayah> {
        return when (surahNumber) {
            1 -> listOf(
                Ayah(1, 1, "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ", "In the name of Allah, the Entirely Merciful, the Especially Merciful.", "শুরু করছি আল্লাহর নামে যিনি পরম করুণাময়, অতি দয়ালু।"),
                Ayah(2, 1, "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ", "[All] praise is [due] to Allah, Lord of the worlds -", "সমস্ত প্রশংসা আল্লাহ তাআলার যিনি সমগ্র সৃষ্টির পালনকর্তা।"),
                Ayah(3, 1, "الرَّحْمَٰنِ الرَّحِيمِ", "The Entirely Merciful, the Especially Merciful,", "যিনি পরম করুণাময় ও অতি দয়ালু।"),
                Ayah(4, 1, "مَالِكِ يَوْمِ الدِّينِ", "Sovereign of the Day of Recompense.", "যিনি বিচার দিবসের মালিক।"),
                Ayah(5, 1, "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ", "It is You we worship and You we ask for help.", "আমরা একমাত্র আপনারই ইবাদত করি এবং শুধুমাত্র আপনারই সাহায্য প্রার্থনা করি।"),
                Ayah(6, 1, "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ", "Guide us to the straight path -", "আমাদেরকে সরল সঠিক পথ প্রদর্শন করুন।"),
                Ayah(7, 1, "صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ", "The path of those upon whom You have bestowed favor, not of those who have evoked [Your] anger or of those who are astray.", "সে সমস্ত লোকদের পথ, যাদেরকে আপনি নেয়ামত দান করেছেন। তাদের পথ নয়, যাদের প্রতি আপনার গজব বর্ষিত হয়েছে এবং যারা পথভ্রষ্ট হয়েছে।")
            )
            2 -> listOf(
                Ayah(1, 2, "الم", "Alif, Lam, Meem.", "আলিফ-লাম-মীম।"),
                Ayah(2, 2, "ذَٰلِكَ الْكِتَابُ لَا رَيْبَ ۛ فِيهِ ۛ هُدًى لِّلْمُتَّقِينَ", "This is the Book about which there is no doubt, a guidance for those conscious of Allah -", "এ সেই কিতাব যাতে কোনই সন্দেহ নেই, পথ প্রদর্শনকারী পরহেযগারদের জন্য,"),
                Ayah(3, 2, "الَّذِينَ يُؤْمِنُونَ بِالْغَيْبِ وَيُقِيمُونَ الصَّلَاةَ وَمِمَّا رَزَقْنَاهُمْ يُنفِقُونَ", "Who believe in the unseen, establish prayer, and spend out of what We have provided for them,", "যারা অদৃশ্যের প্রতি বিশ্বাস স্থাপন করে, নামায প্রতিষ্ঠা করে এবং আমি তাদেরকে যা দান করেছি তা থেকে ব্যয় করে।"),
                Ayah(255, 2, "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ ۚ لَّهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ ۗ مَن ذَا الَّذِي يَشْفَعُ عِندَهُ إِلَّا بِإِذْنِهِ ۚ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ ۖ وَلَا يُحِيطُونَ بِشَيْءٍ مِّنْ عِلْمِهِ إِلَّا بِمَا شَاءَ ۚ وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالْأَرْضَ ۖ وَلَا يَئُودُهُ حِفْظُهُمَا ۚ وَهُوَ الْعَلِيُّ الْعَظِيمُ", "Allah! There is no deity except Him, the Ever-Living, the Sustainer of all existence. Neither drowsiness overtakes Him nor sleep. To Him belongs whatever is in the heavens and whatever is on the earth. Who is it that could intercede with Him except by His permission? He knows what is before them and what will be after them, and they encompass not a thing of His knowledge except for what He wills. His Kursi extends over the heavens and the earth, and their preservation tires Him not. And He is the Most High, the Most Great. (Ayat al-Kursi)", "আল্লাহ, যিনি ব্যতীত অন্য কোন উপাস্য নেই; তিনি চিরঞ্জীব, সবকিছুর ধারক। তন্দ্রা ও নিদ্রা তাঁকে স্পর্শ করে না। নভোমণ্ডলে ও ভূমণ্ডলে যা কিছু রয়েছে সবই তাঁর। কে আছে এমন, যে তাঁর অনুমতি ব্যতীত তাঁর কাছে সুপারিশ করবে? তাদের সামনে ও পেছনে যা কিছু রয়েছে তিনি সবই জানেন। তিনি যা ইচ্ছা করেন তা ব্যতীত তাঁর জ্ঞানের কিছুই তারা আয়ত্ত করতে পারে না। তাঁর সিংহাসন সমস্ত আকাশ ও পৃথিবীকে পরিবেষ্টিত করে আছে এবং এ দুটির সংরক্ষণ তাঁকে পরিশ্রান্ত করে না। তিনি সর্বোচ্চ, সর্বশ্রেষ্ঠ। (আয়াতুল কুরসী)")
            )
            36 -> listOf(
                Ayah(1, 36, "يس", "Ya-Sin.", "ইয়া-সীন।"),
                Ayah(2, 36, "وَالْقُرْآنِ الْحَكِيمِ", "By the wise Qur'an,", "প্রজ্ঞাময় কুরআনের শপথ,"),
                Ayah(3, 36, "إِنَّكَ لَمِنَ الْمُرْسَلِينَ", "Indeed you, [O Muhammad], are from among the messengers,", "নিশ্চয় আপনি রসূলগণের অন্যতম,"),
                Ayah(4, 36, "عَلَىٰ صِرَاطٍ مُّسْتَقِيمٍ", "On a straight path.", "সরল সঠিক পথের ওপর প্রতিষ্ঠিত।"),
                Ayah(5, 36, "تَنزِيلَ الْعَزِيزِ الرَّحِيمِ", "[This is] a revelation of the Exalted in Might, the Merciful,", "ইহা পরাক্রমশালী, পরম দয়ালু আল্লাহর অবতীর্ণ কিতাব।")
            )
            55 -> listOf(
                Ayah(1, 55, "الرَّحْمَٰنُ", "The Most Merciful", "পরম করুণাময় আল্লাহ,"),
                Ayah(2, 55, "عَلَّمَ الْقُرْآنَ", "Taught the Qur'an,", "শিক্ষা দিয়েছেন আল-কুরআন,"),
                Ayah(3, 55, "خَلَقَ الْإِنسَانَ", "Created man,", "সৃষ্টি করেছেন মানবজাতি,"),
                Ayah(4, 55, "عَلَّمَهُ الْبَيَانَ", "[And] taught him eloquence.", "তাকে শিখিয়েছেন ভাব প্রকাশ ও ভাষা।"),
                Ayah(13, 55, "فَبِأَيِّ آلَاءِ رَبِّكُمَا تُكَذِّبَانِ", "So which of the favors of your Lord would you deny?", "অতএব তোমরা উভয়ে তোমাদের প্রতিপালকের কোন্ কোন্ নেয়ামতকে অস্বীকার করবে?")
            )
            67 -> listOf(
                Ayah(1, 67, "تَبَارَكَ الَّذِي بِيَدِهِ الْمُلْكُ وَهُوَ عَلَىٰ كُلِّ شَيْءٍ قَدِيرٌ", "Blessed is He in whose hand is dominion, and He is over all things competent -", "বরকতময় তিনি যাঁর হাতে সর্বময় কর্তৃত্ব এবং তিনি সব কিছুর ওপর ক্ষমতাবান,"),
                Ayah(2, 67, "الَّذِي خَلَقَ الْمَوْتَ وَالْحَيَاةَ لِيَبْلُوَكُمْ أَيُّكُمْ أَحْسَنُ عَمَلًا ۚ وَهُوَ الْعَزِيزُ الْغَفُورُ", "[He] who created death and life to test you [as to] which of you is best in deed - and He is the Exalted in Might, the Forgiving -", "যিনি সৃষ্টি করেছেন মরণ ও জীবন, যাতে তোমাদেরকে পরীক্ষা করেন কে তোমাদের মধ্যে কর্মে শ্রেষ্ঠ? তিনি পরাক্রমশালী, ক্ষমাশীল।"),
                Ayah(3, 67, "الَّذِي خَلَقَ سَبْعَ سَمَاوَاتٍ طِبَاقًا ۖ مَّا تَرَىٰ فِي خَلْقِ الرَّحْمَٰنِ مِن تَفَاوُتٍ ۖ فَارْجِعِ الْبَصَرَ هَلْ تَرَىٰ مِن فُطُورٍ", "[And] who created seven heavens in layers. You do not see in the creation of the Most Merciful any inconsistency. So return your vision to the sky; do you see any breaks?", "যিনি সাত আকাশ স্তরে স্তরে সৃষ্টি করেছেন। পরম করুণাময়ের সৃষ্টিতে তুমি কোন খুঁত দেখতে পাবে না; দৃষ্টি ফিরিয়ে দেখ, কোন ত্রুটি দেখতে পাও কি?")
            )
            93 -> listOf(
                Ayah(1, 93, "وَالضُّحَىٰ", "By the morning brightness", "শপথ পূর্বাহ্ণের রোদের,"),
                Ayah(2, 93, "وَاللَّيْلِ إِذَا سَجَىٰ", "And [by] the night when it covers with darkness,", "এবং শপথ রাত্রির যখন তা নিঝুম হয়ে যায়,"),
                Ayah(3, 93, "مَا وَدَّعَكَ رَبُّكَ وَمَا قَلَىٰ", "Your Lord has not taken leave of you, [O Muhammad], nor has He detested [you].", "আপনার পালনকর্তা আপনাকে ত্যাগ করেননি এবং আপনার প্রতি অসন্তুষ্ট হননি।"),
                Ayah(4, 93, "وَلَلْآخِرَةُ خَيْرٌ لَّكَ مِنَ الْأُولَىٰ", "And the Hereafter is better for you than the first [life].", "এবং নিঃসন্দেহে আপনার জন্য পরবর্তী কাল পূর্ববর্তী কালের চেয়ে শ্রেয়।"),
                Ayah(5, 93, "وَلَسَوْفَ يُعْطِيكَ رَبُّكَ فَتَرْضَىٰ", "And your Lord is going to give you, and you will be satisfied.", "আর শীঘ্রই আপনার পালনকর্তা আপনাকে এমন দান করবেন যে আপনি সন্তুষ্ট হয়ে যাবেন।")
            )
            94 -> listOf(
                Ayah(1, 94, "أَلَمْ نَشْرَحْ لَكَ صَدْرَكَ", "Did We not expand for you, [O Muhammad], your breast?", "আমি কি আপনার বক্ষ আপনার জন্য প্রশস্ত করে দেইনি?"),
                Ayah(2, 94, "وَوَضَعْنَا عَنكَ وِزْرَكَ", "And We removed from you your burden", "এবং আপনার ওপর থেকে নামিয়ে দিয়েছি আপনার সেই গুরুভার,"),
                Ayah(5, 94, "فَإِنَّ مَعَ الْعُسْرِ يُسْرًا", "For indeed, with hardship [will be] ease.", "নিশ্চয় কষ্টের সাথেই স্বস্তি রয়েছে,"),
                Ayah(6, 94, "إِنَّ مَعَ الْعُسْرِ يُسْرًا", "Indeed, with hardship [will be] ease.", "নিঃসন্দেহে কষ্টের সাথেই স্বস্তি রয়েছে।")
            )
            97 -> listOf(
                Ayah(1, 97, "إِنَّا أَنزَلْنَاهُ فِي لَيْلَةِ الْقَدْرِ", "Indeed, We sent the Qur'an down during the Night of Decree.", "নিশ্চয় আমি একে অবতীর্ণ করেছি মহিমান্বিত রজনীতে।"),
                Ayah(2, 97, "وَمَا أَدْرَاكَ مَا لَيْلَةُ الْقَدْرِ", "And what can make you know what is the Night of Decree?", "আর আপনি কি জানেন মহিমান্বিত রজনী কী?"),
                Ayah(3, 97, "لَيْلَةُ الْقَدْرِ خَيْرٌ مِّنْ أَلْفِ شَهْرٍ", "The Night of Decree is better than a thousand months.", "মহিমান্বিত রজনী এক হাজার মাসের চেয়েও শ্রেষ্ঠ।"),
                Ayah(4, 97, "تَنَزَّلُ الْمَلَائِكَةُ وَالرُّوحُ فِيهَا بِإِذْنِ رَبِّهِم مِّن كُلِّ أَمْرٍ", "The angels and the Spirit descend therein by permission of their Lord for every matter.", "সে রাতে ফেরেশতাগণ ও রূহ (জিবরাঈল) তাঁদের পালনকর্তার নির্দেশে প্রতিটি হুকুম নিয়ে অবতীর্ণ হন।"),
                Ayah(5, 97, "سَلَامٌ هِيَ حَتَّىٰ مَطْلَعِ الْفَجْرِ", "Peace it is until the emergence of dawn.", "শান্তিময় সেই রাত, যা ফজর উদয় পর্যন্ত অব্যাহত থাকে।")
            )
            108 -> listOf(
                Ayah(1, 108, "إِنَّا أَعْطَيْنَاكَ الْكَوْثَرَ", "Indeed, We have granted you, [O Muhammad], al-Kawthar.", "নিশ্চয় আমি আপনাকে কাউসার (প্রচুর কল্যাণ) দান করেছি।"),
                Ayah(2, 108, "فَصَلِّ لِرَبِّكَ وَانْحَرْ", "So pray to your Lord and sacrifice [to Him alone].", "অতএব আপনার পালনকর্তার উদ্দেশ্যে নামায পড়ুন এবং কুরবানী করুন।"),
                Ayah(3, 108, "إِنَّ شَانِئَكَ هُوَ الْأَبْتَرُ", "Indeed, your enemy is the one cut off.", "নিশ্চয় আপনার শত্রুই নির্বংশ ও ছিন্নমূল।")
            )
            112 -> listOf(
                Ayah(1, 112, "قُلْ هُوَ اللَّهُ أَحَدٌ", "Say, \"He is Allah, [who is] One,", "বলুন, তিনি আল্লাহ, একক-অদ্বিতীয়,"),
                Ayah(2, 112, "اللَّهُ الصَّمَدُ", "Allah, the Eternal Refuge.", "আল্লাহ কারো মুখাপেক্ষী নন, সকলেই তাঁর মুখাপেক্ষী।"),
                Ayah(3, 112, "لَمْ يَلِدْ وَلَمْ يُولَدْ", "He neither begets nor is born,", "তিনি কাউকে জন্ম দেননি এবং তিনিও কারো থেকে জন্ম নেননি,"),
                Ayah(4, 112, "وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ", "Nor is there to Him any equivalent.\"", "এবং তাঁর সমতুল্য কেউই নেই।")
            )
            113 -> listOf(
                Ayah(1, 113, "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ", "Say, \"I seek refuge in the Lord of daybreak", "বলুন, আমি আশ্রয় প্রার্থনা করছি উষার পালনকর্তার কাছে,"),
                Ayah(2, 113, "مِن شَرِّ مَا خَلَقَ", "From the evil of that which He created", "তিনি যা সৃষ্টি করেছেন তার অনিষ্ট থেকে,"),
                Ayah(3, 113, "وَمِن شَرِّ غَاسِقٍ إِذَا وَقَبَ", "And from the evil of darkness when it settles", "এবং অন্ধকার রাত্রির অনিষ্ট থেকে যখন তা সমাগত হয়,"),
                Ayah(4, 113, "وَمِن شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ", "And from the evil of the blowers in knots", "এবং গ্রন্থিতে ফুঁকদানকারী নারীদের (জাদুকরদের) অনিষ্ট থেকে,"),
                Ayah(5, 113, "وَمِن شَرِّ حَاسِدٍ إِذَا حَسَدَ", "And from the evil of an envier when he envies.\"", "এবং হিংসুকের অনিষ্ট থেকে যখন সে হিংসা করে।")
            )
            114 -> listOf(
                Ayah(1, 114, "قُلْ أَعُوذُ بِرَبِّ النَّاسِ", "Say, \"I seek refuge in the Lord of mankind,", "বলুন, আমি আশ্রয় চাই মানুষের পালনকর্তার কাছে,"),
                Ayah(2, 114, "مَلِكِ النَّاسِ", "The Sovereign of mankind,", "মানুষের অধিপতির কাছে,"),
                Ayah(3, 114, "إِلَٰهِ النَّاسِ", "The God of mankind,", "মানুষের উপাস্যের কাছে,"),
                Ayah(4, 114, "مِن شَرِّ الْوَسْوَاسِ الْخَنَّاسِ", "From the evil of the retreating whisperer -", "আত্মগোপনকারী কুমন্ত্রণাদাতার অনিষ্ট হতে,"),
                Ayah(5, 114, "الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ", "Who whispers [evil] into the breasts of mankind -", "যে মানুষের অন্তরে কুমন্ত্রণা দেয়,"),
                Ayah(6, 114, "مِنَ الْجِنَّةِ وَالنَّاسِ", "From among the jinn and mankind.\"", "জিনদের মধ্য থেকে কিংবা মানুষের মধ্য থেকে।")
            )
            else -> {
                val surah = SURAH_LIST.firstOrNull { it.number == surahNumber }
                val count = surah?.totalVerses ?: 5
                val sampleCount = count.coerceAtMost(7)
                (1..sampleCount).map { i ->
                    Ayah(
                        numberInSurah = i,
                        surahNumber = surahNumber,
                        textArabic = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ • آيَةٌ كَرِيمَةٌ مِنَ السُّورَةِ الْمُبَارَكَةِ رقم $i",
                        textEnglish = "In the name of Allah, Most Gracious, Most Merciful. Verse $i of Surah ${surah?.nameEnglish ?: "Quran"}.",
                        textBengali = "পরম করুণাময় ও দয়ালু আল্লাহর নামে। সূরা ${surah?.nameBengali ?: "কুরআন"}-এর $i নং আয়াত।"
                    )
                }
            }
        }
    }
}
