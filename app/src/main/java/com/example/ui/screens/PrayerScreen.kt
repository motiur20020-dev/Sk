package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.AsrMethod
import com.example.model.CalculationMethod
import com.example.model.PrayerName
import com.example.ui.components.CitySelectionDialog
import com.example.ui.components.PrayerItemRow
import com.example.ui.theme.IslamicGold
import com.example.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayerScreen(
    mainViewModel: MainViewModel,
    onRequestGps: () -> Unit,
    modifier: Modifier = Modifier
) {
    val schedule by mainViewModel.prayerSchedule.collectAsState()
    val language by mainViewModel.preferencesManager.language.collectAsState()
    val calcMethod by mainViewModel.preferencesManager.calculationMethod.collectAsState()
    val asrMethod by mainViewModel.preferencesManager.asrMethod.collectAsState()
    val masterNotif by mainViewModel.preferencesManager.masterNotificationEnabled.collectAsState()
    val masterAdhan by mainViewModel.preferencesManager.masterAdhanEnabled.collectAsState()

    var showCityDialog by remember { mutableStateOf(false) }
    var showMethodMenu by remember { mutableStateOf(false) }

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
            .testTag("prayer_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Location Header & Date
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White.copy(alpha = 0.2f),
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { showCityDialog = true }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = IslamicGold,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = schedule?.locationName ?: "Select Location",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Text(
                            text = schedule?.hijriDate ?: "",
                            style = MaterialTheme.typography.labelSmall,
                            color = IslamicGold
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    val next = schedule?.nextPrayer
                    val prayerTitle = if (language == AppLanguage.BENGALI) next?.nameBn ?: "" else next?.nameEn ?: ""
                    Text(
                        text = if (language == AppLanguage.BENGALI) "পরবর্তী নামায: $prayerTitle" else "Next: $prayerTitle",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${if (language == AppLanguage.BENGALI) "সময়" else "Time"}: ${next?.timeFormatted ?: "--:--"}",
                            style = MaterialTheme.typography.titleMedium,
                            color = IslamicGold
                        )

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color.Black.copy(alpha = 0.35f)
                        ) {
                            Text(
                                text = "${if (language == AppLanguage.BENGALI) "বাকি" else "In"}: ${schedule?.countdownFormatted ?: "00:00:00"}",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        // Sunrise & Sunset info cards
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = IslamicGold.copy(alpha = 0.15f),
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.WbSunny,
                                    contentDescription = "Sunrise",
                                    tint = IslamicGold,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (language == AppLanguage.BENGALI) "সূর্যোদয়" else "Sunrise",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = schedule?.sunriseTime ?: "--:--",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.WbTwilight,
                                    contentDescription = "Sunset",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (language == AppLanguage.BENGALI) "সূর্যাস্ত" else "Sunset",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = schedule?.sunsetTime ?: "--:--",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // 5 Daily Prayers List
        item {
            Text(
                text = if (language == AppLanguage.BENGALI) "পাঁচ ওয়াক্ত নামাযের সময়" else "Daily Prayer Times",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        items(schedule?.prayers?.size ?: 0) { index ->
            val prayerItem = schedule?.prayers?.get(index) ?: return@items
            PrayerItemRow(
                item = prayerItem,
                language = language,
                onToggleNotification = { enabled ->
                    mainViewModel.preferencesManager.setPrayerNotification(prayerItem.prayer.id, enabled)
                    mainViewModel.refreshPrayerSchedule()
                }
            )
        }

        // Calculation & Jurisprudence Method Settings
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (language == AppLanguage.BENGALI) "গণনা পদ্ধতি ও মাযহাব" else "Calculation & Juristic Settings",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Calculation Method picker
                    Text(
                        text = if (language == AppLanguage.BENGALI) "হিসাব পদ্ধতি:" else "Calculation Convention:",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedCard(
                            onClick = { showMethodMenu = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Text(
                                text = calcMethod.displayName,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(14.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = showMethodMenu,
                            onDismissRequest = { showMethodMenu = false }
                        ) {
                            CalculationMethod.entries.forEach { method ->
                                DropdownMenuItem(
                                    text = { Text(method.displayName) },
                                    onClick = {
                                        mainViewModel.preferencesManager.setCalculationMethod(method)
                                        mainViewModel.refreshPrayerSchedule()
                                        showMethodMenu = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Asr Juristic Method chips
                    Text(
                        text = if (language == AppLanguage.BENGALI) "আসর মাযহাবীয় পদ্ধতি:" else "Asr Juristic Method:",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = asrMethod == AsrMethod.HANAFI,
                            onClick = {
                                mainViewModel.preferencesManager.setAsrMethod(AsrMethod.HANAFI)
                                mainViewModel.refreshPrayerSchedule()
                            },
                            label = { Text(if (language == AppLanguage.BENGALI) "হানাফী" else "Hanafi") }
                        )
                        FilterChip(
                            selected = asrMethod == AsrMethod.STANDARD,
                            onClick = {
                                mainViewModel.preferencesManager.setAsrMethod(AsrMethod.STANDARD)
                                mainViewModel.refreshPrayerSchedule()
                            },
                            label = { Text(if (language == AppLanguage.BENGALI) "শাফেঈ / অন্যান্য" else "Standard (Shafi)") }
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                    // Master notification toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (language == AppLanguage.BENGALI) "আজান নোটিফিকেশন" else "Adhan Notifications",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = if (language == AppLanguage.BENGALI) "ওয়াক্তের শুরুতে সতর্কবার্তা" else "Alert at the beginning of each prayer",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = masterNotif,
                            onCheckedChange = { checked ->
                                mainViewModel.preferencesManager.setMasterNotifications(checked)
                                mainViewModel.refreshPrayerSchedule()
                            }
                        )
                    }
                }
            }
        }

        // Methodological Disclaimer card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info",
                        tint = IslamicGold,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = if (language == AppLanguage.BENGALI) {
                            "সতর্কতা: নামাযের সময়সূচী সৌর অবস্থান ও গাণিতিক সূত্রের ভিত্তিতে নির্ণীত। সতর্কতাস্বরূপ স্থানীয় মসজিদের আজানের সাথে ১-২ মিনিট মিলিয়ে নেওয়া উত্তম।"
                        } else {
                            "Note: Prayer times are computed mathematically based on astronomical coordinates. A safety buffer of 1-2 minutes according to your local mosque's timetable is recommended."
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}
