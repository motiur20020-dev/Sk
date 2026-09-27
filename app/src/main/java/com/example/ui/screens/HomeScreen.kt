package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.model.PrayerName
import com.example.ui.components.CitySelectionDialog
import com.example.ui.components.IslamicHeaderBanner
import com.example.ui.components.PrayerItemRow
import com.example.ui.components.QuickActionButton
import com.example.ui.theme.IslamicGold
import com.example.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    onNavigateTab: (String) -> Unit,
    onOpenSubScreen: (String) -> Unit,
    onRequestGps: () -> Unit,
    modifier: Modifier = Modifier
) {
    val schedule by mainViewModel.prayerSchedule.collectAsState()
    val language by mainViewModel.preferencesManager.language.collectAsState()
    var showCityDialog by remember { mutableStateOf(false) }

    if (showCityDialog) {
        CitySelectionDialog(
            cities = mainViewModel.popularCities,
            language = language,
            onSelectCity = { city ->
                mainViewModel.updateLocation(city)
                showCityDialog = false
            },
            onGpsRequest = {
                showCityDialog = false
                onRequestGps()
            },
            onDismiss = { showCityDialog = false }
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App Greeting header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (language == AppLanguage.BENGALI) "আসসালামু আলাইকুম" else "Assalamu Alaikum",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = schedule?.gregorianDate ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Quick test adhan audio button
                val isAdhanPlaying by mainViewModel.isAdhanPlaying.collectAsState()
                Button(
                    onClick = { mainViewModel.playAdhanPreview() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isAdhanPlaying) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primaryContainer,
                        contentColor = if (isAdhanPlaying) Color.White else MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("listen_adhan_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.NotificationsActive,
                        contentDescription = "Adhan",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isAdhanPlaying) {
                            if (language == AppLanguage.BENGALI) "আজান বন্ধ" else "Stop"
                        } else {
                            if (language == AppLanguage.BENGALI) "আজান শুনুন" else "Adhan"
                        },
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }

        // Main Header Banner (Islamic architectural background + Next prayer countdown)
        item {
            IslamicHeaderBanner(
                schedule = schedule,
                language = language,
                onLocationClick = { showCityDialog = true }
            )
        }

        // Quick Navigation Buttons Grid
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        QuickActionButton(
                            title = if (language == AppLanguage.BENGALI) "আল-কুরআন" else "Quran",
                            icon = Icons.Default.MenuBook,
                            onClick = { onNavigateTab("quran") },
                            testTag = "quick_quran"
                        )
                        QuickActionButton(
                            title = if (language == AppLanguage.BENGALI) "ক্বিবলা" else "Qibla",
                            icon = Icons.Default.Explore,
                            onClick = { onNavigateTab("qibla") },
                            testTag = "quick_qibla"
                        )
                        QuickActionButton(
                            title = if (language == AppLanguage.BENGALI) "দো‘আ ও যিকির" else "Duas",
                            icon = Icons.Default.VolunteerActivism,
                            onClick = { onOpenSubScreen("dua") },
                            testTag = "quick_duas"
                        )
                        QuickActionButton(
                            title = if (language == AppLanguage.BENGALI) "ডিজিটাল তাসবীহ" else "Tasbih",
                            icon = Icons.Default.TouchApp,
                            onClick = { onOpenSubScreen("tasbih") },
                            testTag = "quick_tasbih"
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        QuickActionButton(
                            title = if (language == AppLanguage.BENGALI) "হিজরি ক্যালেন্ডার" else "Calendar",
                            icon = Icons.Default.CalendarMonth,
                            onClick = { onOpenSubScreen("calendar") },
                            testTag = "quick_calendar"
                        )
                        QuickActionButton(
                            title = if (language == AppLanguage.BENGALI) "রমযান স্পেশাল" else "Ramadan",
                            icon = Icons.Default.Nightlight,
                            onClick = { onOpenSubScreen("ramadan") },
                            testTag = "quick_ramadan"
                        )
                        QuickActionButton(
                            title = if (language == AppLanguage.BENGALI) "নামাযের সময়" else "Prayers",
                            icon = Icons.Default.Mosque,
                            onClick = { onNavigateTab("prayer") },
                            testTag = "quick_prayers"
                        )
                        QuickActionButton(
                            title = if (language == AppLanguage.BENGALI) "বুকমার্কস" else "Saved",
                            icon = Icons.Default.AutoAwesome,
                            onClick = { onOpenSubScreen("bookmarks") },
                            testTag = "quick_bookmarks"
                        )
                    }
                }
            }
        }

        // Today's 5 Prayers card
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (language == AppLanguage.BENGALI) "আজকের নামাযের সময়সূচী" else "Today's Prayer Schedule",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    TextButton(onClick = { onNavigateTab("prayer") }) {
                        Text(
                            text = if (language == AppLanguage.BENGALI) "সব দেখুন" else "View All",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                schedule?.prayers?.forEach { item ->
                    PrayerItemRow(
                        item = item,
                        language = language,
                        onToggleNotification = { enabled ->
                            mainViewModel.preferencesManager.setPrayerNotification(item.prayer.id, enabled)
                            mainViewModel.refreshPrayerSchedule()
                        }
                    )
                }
            }
        }

        // Ayat of the Day Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("verse_of_the_day_card")
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.FormatQuote,
                                contentDescription = null,
                                tint = IslamicGold,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (language == AppLanguage.BENGALI) "আজকের আয়াত" else "Ayah of the Day",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Text(
                            text = "সূরা আল-বাক্বারাহ (২:২৫৫)",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ ۚ لَّهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp, lineHeight = 30.sp),
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (language == AppLanguage.BENGALI) {
                            "\"আল্লাহ, যিনি ব্যতীত অন্য কোন উপাস্য নেই; তিনি চিরঞ্জীব, সবকিছুর ধারক। তন্দ্রা ও নিদ্রা তাঁকে স্পর্শ করে না।\""
                        } else {
                            "\"Allah! There is no deity except Him, the Ever-Living, the Sustainer of all existence. Neither drowsiness overtakes Him nor sleep.\""
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Daily Hadith Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (language == AppLanguage.BENGALI) "আজকের হাদিস" else "Hadith of the Day",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (language == AppLanguage.BENGALI) {
                            "রাসূলুল্লাহ (সাঃ) বলেছেন: \"যে ব্যক্তি মানুষের প্রতি অনুগ্রহ ও দয়া প্রদর্শন করে না, আল্লাহও তার প্রতি অনুগ্রহ করেন না।\" (সহীহ বুখারী ও মুসলিম)"
                        } else {
                            "The Messenger of Allah (peace be upon him) said: \"Whoever does not show mercy to people, Allah will not show mercy to him.\" (Sahih al-Bukhari & Muslim)"
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}
