package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.ui.theme.IslamicGold
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.TasbihViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasbihScreen(
    tasbihViewModel: TasbihViewModel,
    mainViewModel: MainViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val language by mainViewModel.preferencesManager.language.collectAsState()
    val count by tasbihViewModel.currentCount.collectAsState()
    val target by tasbihViewModel.targetCount.collectAsState()
    val selectedPreset by tasbihViewModel.selectedPreset.collectAsState()
    val history by tasbihViewModel.historyRecords.collectAsState()
    val vibrateEnabled by mainViewModel.preferencesManager.tasbihVibrate.collectAsState()

    var showResetDialog by remember { mutableStateOf(false) }
    var showHistoryDialog by remember { mutableStateOf(false) }

    // Tap scale animation
    var isPressed by remember { mutableStateOf(false) }
    val buttonScale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1.0f,
        animationSpec = spring(dampingRatio = 0.4f, stiffness = 400f),
        label = "tasbihScale"
    )

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = {
                Text(
                    text = if (language == AppLanguage.BENGALI) "কাউন্টার রিসেট করবেন?" else "Reset Counter?",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    if (language == AppLanguage.BENGALI) "বর্তমান গণনাটি ইতিহাসে সংরক্ষণ করে কাউন্টার শূন্য করা হবে।"
                    else "The current count will be saved to your history and reset to zero."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        tasbihViewModel.resetCount()
                        showResetDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(if (language == AppLanguage.BENGALI) "হ্যাঁ, রিসেট করুন" else "Reset")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text(if (language == AppLanguage.BENGALI) "বাতিল" else "Cancel")
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }

    if (showHistoryDialog) {
        AlertDialog(
            onDismissRequest = { showHistoryDialog = false },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (language == AppLanguage.BENGALI) "তাসবীহ ইতিহাস" else "Tasbih History",
                        fontWeight = FontWeight.Bold
                    )
                    if (history.isNotEmpty()) {
                        IconButton(onClick = { tasbihViewModel.clearAllHistory() }) {
                            Icon(Icons.Default.DeleteSweep, contentDescription = "Clear History", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            },
            text = {
                if (history.isEmpty()) {
                    Text(
                        text = if (language == AppLanguage.BENGALI) "কোনো ইতিহাস রেকর্ড পাওয়া যায়নি।" else "No history records found yet.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    LazyColumn(modifier = Modifier.height(280.dp)) {
                        items(history) { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(text = item.dhikrName, fontWeight = FontWeight.Bold)
                                    Text(
                                        text = item.dateFormatted,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Text(
                                    text = "${item.count} বার",
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showHistoryDialog = false }) {
                    Text(if (language == AppLanguage.BENGALI) "ঠিক আছে" else "Close")
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("tasbih_screen")
    ) {
        TopAppBar(
            title = {
                Text(
                    text = if (language == AppLanguage.BENGALI) "ডিজিটাল তাসবীহ" else "Digital Tasbih",
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { showHistoryDialog = true }) {
                    Icon(Icons.Default.History, contentDescription = "History")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Dhikr Presets horizontal chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(tasbihViewModel.presets) { preset ->
                        FilterChip(
                            selected = preset.id == selectedPreset.id,
                            onClick = { tasbihViewModel.selectPreset(preset) },
                            label = {
                                Text(if (language == AppLanguage.BENGALI) preset.transliterationBn else preset.transliterationEn)
                            }
                        )
                    }
                }
            }

            // Selected Dhikr Display Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = selectedPreset.arabicText,
                            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 26.sp),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (language == AppLanguage.BENGALI) selectedPreset.transliterationBn else selectedPreset.transliterationEn,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (language == AppLanguage.BENGALI) selectedPreset.meaningBn else selectedPreset.meaningEn,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Target selector row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${if (language == AppLanguage.BENGALI) "লক্ষ্যমাত্রা" else "Target"}:",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    listOf(33, 99, 100, 1000).forEach { t ->
                        FilterChip(
                            selected = target == t,
                            onClick = { tasbihViewModel.setTargetCount(t) },
                            label = { Text(t.toString()) },
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }
            }

            // Main Interactive Circular Tap Button
            item {
                val progress = if (target > 0) (count % target) / target.toFloat() else 0f

                Box(
                    modifier = Modifier
                        .padding(vertical = 14.dp)
                        .size(240.dp)
                        .scale(buttonScale)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .border(
                            width = 6.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                isPressed = true
                                tasbihViewModel.increment()
                                isPressed = false
                            }
                        )
                        .testTag("tasbih_tap_button"),
                    contentAlignment = Alignment.Center
                ) {
                    // Circular progress arc
                    CircularProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxSize(),
                        color = IslamicGold,
                        strokeWidth = 6.dp
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = count.toString(),
                            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 54.sp),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "${if (language == AppLanguage.BENGALI) "টার্গেট" else "Target"}: ${if (target > 0) target else "∞"}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (language == AppLanguage.BENGALI) "ট্যাপ করুন" else "TAP TO COUNT",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Actions row: Reset and Haptic Toggle
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { showResetDialog = true },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(Icons.Default.RestartAlt, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (language == AppLanguage.BENGALI) "রিসেট" else "Reset")
                    }

                    FilterChip(
                        selected = vibrateEnabled,
                        onClick = { mainViewModel.preferencesManager.setTasbihVibrate(!vibrateEnabled) },
                        label = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Vibration, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (language == AppLanguage.BENGALI) "ভাইব্রেশন" else "Vibration")
                            }
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
