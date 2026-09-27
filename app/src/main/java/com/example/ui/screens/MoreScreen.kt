package com.example.ui.screens

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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.AppLanguage
import com.example.ui.theme.IslamicGold
import com.example.viewmodel.MainViewModel

@Composable
fun MoreScreen(
    mainViewModel: MainViewModel,
    onOpenSubScreen: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val language by mainViewModel.preferencesManager.language.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("more_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = if (language == AppLanguage.BENGALI) "ইসলামিক অন্যান্য ফিচার" else "More Islamic Features",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        item {
            MoreMenuCard(
                title = if (language == AppLanguage.BENGALI) "দো‘আ ও আযকার" else "Duas & Azkar",
                subtitle = if (language == AppLanguage.BENGALI) "সকাল-সন্ধ্যা ও দৈনন্দিন জীবনের প্রামাণ্য দো‘আ" else "Authentic daily, morning & evening supplications",
                icon = Icons.Default.VolunteerActivism,
                iconTint = MaterialTheme.colorScheme.primary,
                onClick = { onOpenSubScreen("dua") },
                testTag = "more_dua_card"
            )
        }

        item {
            MoreMenuCard(
                title = if (language == AppLanguage.BENGALI) "ডিজিটাল তাসবীহ" else "Digital Tasbih",
                subtitle = if (language == AppLanguage.BENGALI) "স্মার্ট কাউন্টার, স্পর্শ প্রতিক্রিয়া ও ইতিহাস" else "Tactile digital counter with dhikr presets and history",
                icon = Icons.Default.TouchApp,
                iconTint = IslamicGold,
                onClick = { onOpenSubScreen("tasbih") },
                testTag = "more_tasbih_card"
            )
        }

        item {
            MoreMenuCard(
                title = if (language == AppLanguage.BENGALI) "হিজরি ইসলামিক ক্যালেন্ডার" else "Islamic Hijri Calendar",
                subtitle = if (language == AppLanguage.BENGALI) "গুরুত্বপূর্ণ ইসলামিক দিবস ও মাসসমূহ" else "Sacred Islamic dates, holidays and events",
                icon = Icons.Default.CalendarMonth,
                iconTint = MaterialTheme.colorScheme.primary,
                onClick = { onOpenSubScreen("calendar") },
                testTag = "more_calendar_card"
            )
        }

        item {
            MoreMenuCard(
                title = if (language == AppLanguage.BENGALI) "রমযান মোবারক হাব" else "Ramadan Mode",
                subtitle = if (language == AppLanguage.BENGALI) "সেহরি ও ইফতার কাউন্টডাউন, রোযা ট্র্যাকার" else "Suhoor & Iftar countdown, fasting tracker & goals",
                icon = Icons.Default.Nightlight,
                iconTint = IslamicGold,
                onClick = { onOpenSubScreen("ramadan") },
                testTag = "more_ramadan_card"
            )
        }

        item {
            MoreMenuCard(
                title = if (language == AppLanguage.BENGALI) "সংরক্ষিত বুকমার্কস" else "Bookmarks & Favorites",
                subtitle = if (language == AppLanguage.BENGALI) "সংরক্ষিত কুরআনের আয়াত ও দো‘আ" else "Your saved Quran ayahs and favorite supplications",
                icon = Icons.Default.Bookmark,
                iconTint = MaterialTheme.colorScheme.primary,
                onClick = { onOpenSubScreen("bookmarks") },
                testTag = "more_bookmarks_card"
            )
        }

        item {
            MoreMenuCard(
                title = if (language == AppLanguage.BENGALI) "সেটিংস ও পছন্দসমূহ" else "Settings",
                subtitle = if (language == AppLanguage.BENGALI) "ভাষা, থিম, হিসাব পদ্ধতি ও আজান কনফিগারেশন" else "Language, theme, calculation method & notifications",
                icon = Icons.Default.Settings,
                iconTint = MaterialTheme.colorScheme.onSurfaceVariant,
                onClick = { onOpenSubScreen("settings") },
                testTag = "more_settings_card"
            )
        }

        item {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
fun MoreMenuCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color,
    onClick: () -> Unit,
    testTag: String = ""
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable { onClick() }
            .testTag(testTag),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = iconTint.copy(alpha = 0.15f),
                modifier = Modifier.size(46.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
