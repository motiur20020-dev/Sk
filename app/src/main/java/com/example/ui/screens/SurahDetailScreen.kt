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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.Ayah
import com.example.model.Surah
import com.example.ui.theme.IslamicGold
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.QuranViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurahDetailScreen(
    quranViewModel: QuranViewModel,
    mainViewModel: MainViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val surah by quranViewModel.currentSurah.collectAsState()
    val ayahs by quranViewModel.currentAyahs.collectAsState()
    val language by mainViewModel.preferencesManager.language.collectAsState()
    val fontSizeSp by mainViewModel.preferencesManager.quranFontSizeSp.collectAsState()
    val isPlayingAudio by quranViewModel.isPlayingAudio.collectAsState()
    val isBufferingAudio by quranViewModel.isBufferingAudio.collectAsState()

    var showFontSizeSheet by remember { mutableStateOf(false) }

    if (surah == null) return

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("surah_detail_screen")
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = if (language == AppLanguage.BENGALI) surah!!.nameBengali else surah!!.nameEnglish,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${if (language == AppLanguage.BENGALI) surah!!.revelationType.bn else surah!!.revelationType.en} • ${surah!!.totalVerses} ${if (language == AppLanguage.BENGALI) "আয়াত" else "Ayahs"}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { showFontSizeSheet = !showFontSizeSheet }) {
                    Icon(
                        imageVector = Icons.Default.FormatSize,
                        contentDescription = "Adjust Font Size",
                        tint = if (showFontSizeSheet) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
        )

        // Font Size Adjuster Panel (Collapsible)
        if (showFontSizeSheet) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (language == AppLanguage.BENGALI) "আরবি ফন্ট সাইজ" else "Arabic Font Size",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${fontSizeSp}sp",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Slider(
                        value = fontSizeSp.toFloat(),
                        onValueChange = { mainViewModel.preferencesManager.setQuranFontSize(it.toInt()) },
                        valueRange = 18f..38f,
                        steps = 5,
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }

        // Surah Bismillah Banner
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = surah!!.nameArabic,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (language == AppLanguage.BENGALI) surah!!.meaningBengali else surah!!.meaningEnglish,
                    style = MaterialTheme.typography.bodyMedium,
                    color = IslamicGold
                )

                if (surah!!.number != 9) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
                        color = Color.White
                    )
                }
            }
        }

        // Audio Player Controls Bar
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { quranViewModel.previousSurah() }) {
                        Icon(Icons.Default.SkipPrevious, contentDescription = "Previous Surah")
                    }

                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(44.dp)
                    ) {
                        IconButton(onClick = { quranViewModel.playSurahAudio(surah!!.number) }) {
                            if (isBufferingAudio) {
                                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                            } else {
                                Icon(
                                    imageVector = if (isPlayingAudio) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = "Play Audio",
                                    tint = Color.White
                                )
                            }
                        }
                    }

                    IconButton(onClick = { quranViewModel.nextSurah() }) {
                        Icon(Icons.Default.SkipNext, contentDescription = "Next Surah")
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Mishary Rashid Alafasy",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = if (isPlayingAudio) "Playing Audio..." else "Audio Recitation",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isPlayingAudio) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // Ayah List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(ayahs, key = { it.numberInSurah }) { ayah ->
                val isBookmarked = quranViewModel.isAyahBookmarked(surah!!.number, ayah.numberInSurah)
                AyahCardItem(
                    ayah = ayah,
                    arabicFontSizeSp = fontSizeSp,
                    language = language,
                    isBookmarked = isBookmarked,
                    onToggleBookmark = { quranViewModel.toggleBookmarkAyah(surah!!, ayah) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun AyahCardItem(
    ayah: Ayah,
    arabicFontSizeSp: Int,
    language: AppLanguage,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("ayah_item_${ayah.numberInSurah}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Ayah number badge & Bookmark row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = ayah.numberInSurah.toString(),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                IconButton(
                    onClick = onToggleBookmark,
                    modifier = Modifier.size(34.dp)
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark Ayah",
                        tint = if (isBookmarked) IslamicGold else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Arabic text (Right-to-Left styling)
            Text(
                text = ayah.textArabic,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = arabicFontSizeSp.sp,
                    lineHeight = (arabicFontSizeSp * 1.6).sp
                ),
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
            Spacer(modifier = Modifier.height(10.dp))

            // Bengali Translation
            Text(
                text = ayah.textBengali,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 22.sp
            )

            // English Translation
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = ayah.textEnglish,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )
        }
    }
}
