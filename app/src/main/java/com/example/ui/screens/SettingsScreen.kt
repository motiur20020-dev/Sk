package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.AppLanguage
import com.example.model.AppThemeMode
import com.example.model.AsrMethod
import com.example.model.CalculationMethod
import com.example.ui.theme.IslamicGold
import com.example.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    mainViewModel: MainViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val prefs = mainViewModel.preferencesManager
    val language by prefs.language.collectAsState()
    val themeMode by prefs.themeMode.collectAsState()
    val calcMethod by prefs.calculationMethod.collectAsState()
    val asrMethod by prefs.asrMethod.collectAsState()
    val quranFontSize by prefs.quranFontSizeSp.collectAsState()
    val masterNotif by prefs.masterNotificationEnabled.collectAsState()
    val masterAdhan by prefs.masterAdhanEnabled.collectAsState()
    val morningReminder by prefs.morningAzkarReminder.collectAsState()
    val eveningReminder by prefs.eveningAzkarReminder.collectAsState()
    val quranReminder by prefs.quranReminder.collectAsState()
    val tasbihVibrate by prefs.tasbihVibrate.collectAsState()

    var showCalcMenu by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("settings_screen")
    ) {
        TopAppBar(
            title = {
                Text(
                    text = if (language == AppLanguage.BENGALI) "সেটিংস ও পছন্দসমূহ" else "Settings",
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
            // Language Selection
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Language, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (language == AppLanguage.BENGALI) "অ্যাপের ভাষা (Language)" else "App Language",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            FilterChip(
                                selected = language == AppLanguage.BENGALI,
                                onClick = {
                                    prefs.setLanguage(AppLanguage.BENGALI)
                                    mainViewModel.refreshPrayerSchedule()
                                },
                                label = { Text("বাংলা (Bengali)") }
                            )
                            FilterChip(
                                selected = language == AppLanguage.ENGLISH,
                                onClick = {
                                    prefs.setLanguage(AppLanguage.ENGLISH)
                                    mainViewModel.refreshPrayerSchedule()
                                },
                                label = { Text("English") }
                            )
                        }
                    }
                }
            }

            // Theme Selection
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Palette, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (language == AppLanguage.BENGALI) "থিম (Theme Mode)" else "Theme Mode",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            AppThemeMode.entries.forEach { mode ->
                                FilterChip(
                                    selected = themeMode == mode,
                                    onClick = { prefs.setThemeMode(mode) },
                                    label = { Text(if (language == AppLanguage.BENGALI) mode.labelBn else mode.labelEn) }
                                )
                            }
                        }
                    }
                }
            }

            // Prayer Calculation Method & Asr Jurisprudence
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Schedule, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (language == AppLanguage.BENGALI) "নামাযের হিসাব ও মাযহাব" else "Prayer Calculation Settings",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = if (language == AppLanguage.BENGALI) "হিসাব পদ্ধতি:" else "Calculation Convention:",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Box(modifier = Modifier.fillMaxWidth()) {
                            OutlinedCard(
                                onClick = { showCalcMenu = true },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                            ) {
                                Text(
                                    text = calcMethod.displayName,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }

                            DropdownMenu(
                                expanded = showCalcMenu,
                                onDismissRequest = { showCalcMenu = false }
                            ) {
                                CalculationMethod.entries.forEach { method ->
                                    DropdownMenuItem(
                                        text = { Text(method.displayName) },
                                        onClick = {
                                            prefs.setCalculationMethod(method)
                                            mainViewModel.refreshPrayerSchedule()
                                            showCalcMenu = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (language == AppLanguage.BENGALI) "আসর নামাযের মাযহাব:" else "Asr Juristic Method:",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Row(
                            modifier = Modifier.padding(vertical = 6.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilterChip(
                                selected = asrMethod == AsrMethod.HANAFI,
                                onClick = {
                                    prefs.setAsrMethod(AsrMethod.HANAFI)
                                    mainViewModel.refreshPrayerSchedule()
                                },
                                label = { Text(if (language == AppLanguage.BENGALI) "হানাফী" else "Hanafi") }
                            )
                            FilterChip(
                                selected = asrMethod == AsrMethod.STANDARD,
                                onClick = {
                                    prefs.setAsrMethod(AsrMethod.STANDARD)
                                    mainViewModel.refreshPrayerSchedule()
                                },
                                label = { Text(if (language == AppLanguage.BENGALI) "শাফেঈ / অন্যান্য" else "Standard (Shafi)") }
                            )
                        }
                    }
                }
            }

            // Notifications & Adhan
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Notifications, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (language == AppLanguage.BENGALI) "নোটিফিকেশন ও আজান" else "Notifications & Adhan",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = if (language == AppLanguage.BENGALI) "নামাযের নোটিফিকেশন" else "Prayer Time Alerts",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = if (language == AppLanguage.BENGALI) "ওয়াক্ত শুরু হলে বার্তা পাঠানো হবে" else "Show reminder notification on prayer time",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Switch(
                                checked = masterNotif,
                                onCheckedChange = {
                                    prefs.setMasterNotifications(it)
                                    mainViewModel.refreshPrayerSchedule()
                                }
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = if (language == AppLanguage.BENGALI) "আজানের ধ্বনি বাজানো" else "Play Adhan Sound",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = if (language == AppLanguage.BENGALI) "নামাযের ওয়াক্তে আজান অডিও" else "Play Adhan audio during notifications",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Switch(
                                checked = masterAdhan,
                                onCheckedChange = { prefs.setMasterAdhan(it) }
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Test adhan button
                        Button(
                            onClick = { mainViewModel.playAdhanPreview() },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer, contentColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.NotificationsActive, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (language == AppLanguage.BENGALI) "আজানের শব্দ পরীক্ষা করুন (Test Adhan)" else "Test Adhan Audio Preview")
                        }
                    }
                }
            }

            // Reminders Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = if (language == AppLanguage.BENGALI) "দৈনিক স্মারকসমূহ" else "Daily Islamic Reminders",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(if (language == AppLanguage.BENGALI) "সকালের যিকির রিমাইন্ডার" else "Morning Azkar Reminder")
                            Switch(
                                checked = morningReminder,
                                onCheckedChange = { prefs.setReminder("morning_azkar", it) }
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(if (language == AppLanguage.BENGALI) "সন্ধ্যার যিকির রিমাইন্ডার" else "Evening Azkar Reminder")
                            Switch(
                                checked = eveningReminder,
                                onCheckedChange = { prefs.setReminder("evening_azkar", it) }
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(if (language == AppLanguage.BENGALI) "দৈনিক কুরআন পাঠের তাগিদ" else "Daily Quran Reading Prompt")
                            Switch(
                                checked = quranReminder,
                                onCheckedChange = { prefs.setReminder("quran", it) }
                            )
                        }
                    }
                }
            }

            // Quran Font Size Setting
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.FormatSize, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = if (language == AppLanguage.BENGALI) "কুরআন আরবি ফন্ট সাইজ" else "Quran Arabic Font Size",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(text = "${quranFontSize}sp", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = quranFontSize.sp),
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(vertical = 6.dp)
                        )

                        Slider(
                            value = quranFontSize.toFloat(),
                            onValueChange = { prefs.setQuranFontSize(it.toInt()) },
                            valueRange = 18f..38f,
                            steps = 5,
                            colors = SliderDefaults.colors(thumbColor = MaterialTheme.colorScheme.primary, activeTrackColor = MaterialTheme.colorScheme.primary)
                        )
                    }
                }
            }

            // About Noor Muslim
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(54.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_app_logo),
                                    contentDescription = "Noor Muslim",
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Noor Muslim",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Your Daily Islamic Companion",
                            style = MaterialTheme.typography.labelMedium,
                            color = IslamicGold,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Version 1.0.0",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = if (language == AppLanguage.BENGALI) {
                                "নূর মুসলিম একটি আধুনিক ও বিশ্বস্ত ইসলামিক অ্যাপ্লিকেশন। কুরআন, নামাযের সময়, কিবলা, তাসবীহ ও দো‘আ সবই এক জায়গায় সহজলভ্য। নামাযের সময় ও কিবলা গণনায় প্রমিত মহাকাশীয় অ্যালগরিদম ব্যবহৃত।"
                            } else {
                                "Noor Muslim is an elegant, privacy-focused Islamic utility app providing prayer times, the Holy Quran, Qibla compass, Tasbih counter, Duas & Azkar, and Ramadan tools completely offline and ad-free."
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}
