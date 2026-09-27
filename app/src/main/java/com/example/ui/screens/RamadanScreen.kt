package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.ui.theme.EmeraldGreenDark
import com.example.ui.theme.EmeraldGreenPrimary
import com.example.ui.theme.IslamicGold
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.RamadanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RamadanScreen(
    ramadanViewModel: RamadanViewModel,
    mainViewModel: MainViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val language by mainViewModel.preferencesManager.language.collectAsState()
    val isFastingNow by ramadanViewModel.isFastingNow.collectAsState()
    val targetName by ramadanViewModel.targetName.collectAsState()
    val countdown by ramadanViewModel.countdownToTarget.collectAsState()
    val suhoorTime by ramadanViewModel.suhoorTimeFormatted.collectAsState()
    val iftarTime by ramadanViewModel.iftarTimeFormatted.collectAsState()

    val quranPages by ramadanViewModel.quranPagesReadToday.collectAsState()
    val quranGoal by ramadanViewModel.quranPageGoal.collectAsState()
    val dhikrCount by ramadanViewModel.dhikrCountToday.collectAsState()
    val dhikrGoal by ramadanViewModel.dhikrGoal.collectAsState()

    val fastingRecords by ramadanViewModel.fastingList.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("ramadan_screen")
    ) {
        TopAppBar(
            title = {
                Text(
                    text = if (language == AppLanguage.BENGALI) "মাহে রমযান স্পেশাল" else "Ramadan Kareem",
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Suhoor & Iftar Live Banner Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (language == AppLanguage.BENGALI) "রমযান মোবারক" else "Ramadan Mubarak",
                                style = MaterialTheme.typography.titleMedium,
                                color = IslamicGold,
                                fontWeight = FontWeight.Bold
                            )
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color.White.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = if (isFastingNow) {
                                        if (language == AppLanguage.BENGALI) "রোযারত অবস্থা" else "Fasting Active"
                                    } else {
                                        if (language == AppLanguage.BENGALI) "ইফতারের পর" else "Post-Iftar"
                                    },
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Live Target Countdown Box
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = if (language == AppLanguage.BENGALI) "$targetName এর বাকি সময়" else "Countdown to $targetName",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                            Text(
                                text = countdown,
                                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 38.sp),
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Sehri and Iftar Times Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.DarkMode, contentDescription = null, tint = IslamicGold, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (language == AppLanguage.BENGALI) "সেহরি শেষ" else "Suhoor Ends",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.White.copy(alpha = 0.8f)
                                    )
                                }
                                Text(
                                    text = suhoorTime,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.LightMode, contentDescription = null, tint = IslamicGold, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (language == AppLanguage.BENGALI) "ইফতার শুরু" else "Iftar Time",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.White.copy(alpha = 0.8f)
                                    )
                                }
                                Text(
                                    text = iftarTime,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Daily Spiritual Goals (Quran & Dhikr progress)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = if (language == AppLanguage.BENGALI) "আজকের আত্মশুদ্ধির লক্ষ্য" else "Today's Spiritual Goals",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Quran Goal
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.MenuBook, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (language == AppLanguage.BENGALI) "কুরআন তিলাওয়াত ($quranPages / $quranGoal পৃষ্ঠা)" else "Quran Reading ($quranPages / $quranGoal pages)",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            IconButton(onClick = { ramadanViewModel.incrementQuranPage() }, modifier = Modifier.size(32.dp)) {
                                Icon(Icons.Default.Add, contentDescription = "Add page", tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                        LinearProgressIndicator(
                            progress = { (quranPages.toFloat() / quranGoal).coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Dhikr Goal
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.TouchApp, contentDescription = null, tint = IslamicGold, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (language == AppLanguage.BENGALI) "তাসবীহ ও যিকির ($dhikrCount / $dhikrGoal বার)" else "Dhikr Goal ($dhikrCount / $dhikrGoal count)",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            IconButton(onClick = { ramadanViewModel.incrementDhikrProgress(25) }, modifier = Modifier.size(32.dp)) {
                                Icon(Icons.Default.Add, contentDescription = "Add Dhikr", tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                        LinearProgressIndicator(
                            progress = { (dhikrCount.toFloat() / dhikrGoal).coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = IslamicGold
                        )
                    }
                }
            }

            // Ramadan Duas Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = if (language == AppLanguage.BENGALI) "রমযানের প্রয়োজনীয় দো‘আ" else "Essential Ramadan Duas",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Iftar Dua
                        Text(
                            text = if (language == AppLanguage.BENGALI) "ইফতারের দো‘আ:" else "Dua for Breaking Fast (Iftar):",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = IslamicGold
                        )
                        Text(
                            text = "ذَهَبَ الظَّمَأُ وَابْتَلَّتِ الْعُرُوقُ وَثَبَتَ الْأَجْرُ إِنْ شَاءَ اللَّهُ",
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                        Text(
                            text = if (language == AppLanguage.BENGALI) "\"পিপাসা নিবারিত হয়েছে, শিরা-উপশিরা সিক্ত হয়েছে এবং ইনশাআল্লাহ প্রতিদানও নির্ধারিত হয়েছে।\""
                            else "\"The thirst has gone, the veins are moistened, and the reward is confirmed, if Allah wills.\"",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Laylat al-Qadr Dua
                        Text(
                            text = if (language == AppLanguage.BENGALI) "শবে কদরের দো‘আ:" else "Dua for Laylat al-Qadr:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = IslamicGold
                        )
                        Text(
                            text = "اللَّهُمَّ إِنَّكَ عَفُوٌّ تُحِبُّ الْعَفْوَ فَاعْفُ عَنِّي",
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                        Text(
                            text = if (language == AppLanguage.BENGALI) "\"হে আল্লাহ! আপনি ক্ষমাশীল, আপনি ক্ষমা করতে ভালোবাসেন; অতএব আমাকে ক্ষমা করে দিন।\""
                            else "\"O Allah, You are Most Forgiving, and You love forgiveness; so forgive me.\"",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // 30 Days Fasting Tracker Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = if (language == AppLanguage.BENGALI) "রোযার ট্র্যাকার (১ - ৩০ রমযান)" else "30-Day Fasting Tracker",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = if (language == AppLanguage.BENGALI) "প্রতিদিনের রোযা সম্পন্ন হলে টিক দিন" else "Check off each day as you complete your fast",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // List of days (1 to 10 sample visible with toggle)
                        (1..10).forEach { dayNum ->
                            val dateStr = "2026-03-${String.format("%02d", dayNum)}"
                            val isFasted = fastingRecords.any { it.hijriDay == dayNum && it.isFasted }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { ramadanViewModel.toggleFastingDay(dayNum, dateStr, isFasted) }
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${if (language == AppLanguage.BENGALI) "রমযান দিন" else "Ramadan Day"} $dayNum",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (isFasted) FontWeight.Bold else FontWeight.Normal
                                )

                                Checkbox(
                                    checked = isFasted,
                                    onCheckedChange = { ramadanViewModel.toggleFastingDay(dayNum, dateStr, isFasted) },
                                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
