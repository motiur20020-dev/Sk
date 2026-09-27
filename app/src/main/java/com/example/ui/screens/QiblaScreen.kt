package com.example.ui.screens

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.AppLanguage
import com.example.prayer.QiblaCalculator
import com.example.sensor.CompassSensorManager
import com.example.ui.theme.EmeraldGreenDark
import com.example.ui.theme.EmeraldGreenPrimary
import com.example.ui.theme.IslamicGold
import com.example.viewmodel.MainViewModel
import kotlin.math.abs

@Composable
fun QiblaScreen(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val language by mainViewModel.preferencesManager.language.collectAsState()
    val location by mainViewModel.preferencesManager.userLocation.collectAsState()

    val compassManager = remember { CompassSensorManager(context) }
    DisposableEffect(Unit) {
        compassManager.startListening()
        onDispose { compassManager.stopListening() }
    }

    val sensorAzimuth by compassManager.azimuth.collectAsState()
    val sensorAvailable by compassManager.sensorAvailable.collectAsState()

    var manualRotationSlider by remember { mutableFloatStateOf(0f) }
    var useManualSlider by remember { mutableStateOf(!sensorAvailable) }

    val currentAzimuth = if (useManualSlider) manualRotationSlider else sensorAzimuth

    val qiblaBearing = remember(location.latitude, location.longitude) {
        QiblaCalculator.calculateQiblaBearing(location.latitude, location.longitude)
    }

    val distanceKm = remember(location.latitude, location.longitude) {
        QiblaCalculator.calculateDistanceToKaabaKm(location.latitude, location.longitude)
    }

    // Difference between phone orientation and Qibla
    var diff = (qiblaBearing - currentAzimuth + 360f) % 360f
    if (diff > 180f) diff -= 360f
    val isFacingQibla = abs(diff) < 4.0f

    // Haptic vibration when aligned
    val vibrator = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vm = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vm?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
    }

    var hasVibratedForCurrentAlignment by remember { mutableStateOf(false) }

    LaunchedEffect(isFacingQibla) {
        if (isFacingQibla && !hasVibratedForCurrentAlignment) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator?.vibrate(VibrationEffect.createOneShot(80, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator?.vibrate(80)
                }
            } catch (_: Exception) {}
            hasVibratedForCurrentAlignment = true
        } else if (!isFacingQibla) {
            hasVibratedForCurrentAlignment = false
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("qibla_screen"),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Location and Heading card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (language == AppLanguage.BENGALI) location.cityNameBn else location.cityNameEn,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${if (language == AppLanguage.BENGALI) "মক্কা থেকে দূরত্ব" else "Distance to Kaaba"}: $distanceKm km",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "${qiblaBearing.toInt()}°",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = if (language == AppLanguage.BENGALI) "ক্বিবলা কোণ" else "Qibla Bearing",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Status alignment banner
        item {
            val statusColor by animateColorAsState(
                targetValue = if (isFacingQibla) EmeraldGreenPrimary else MaterialTheme.colorScheme.surfaceVariant,
                label = "statusBg"
            )

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = statusColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Navigation,
                        contentDescription = null,
                        tint = if (isFacingQibla) Color.White else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isFacingQibla) {
                            if (language == AppLanguage.BENGALI) "মাশাআল্লাহ! আপনি ক্বিবলার মুখোমুখি আছেন।"
                            else "MashaAllah! You are facing the Qibla."
                        } else {
                            val turnDirection = if (diff > 0) "right" else "left"
                            val turnBn = if (diff > 0) "ডানে" else "বামে"
                            if (language == AppLanguage.BENGALI) {
                                "ক্বিবলা পেতে ${abs(diff).toInt()}° $turnBn ঘুরুন"
                            } else {
                                "Rotate ${abs(diff).toInt()}° $turnDirection to face Qibla"
                            }
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isFacingQibla) Color.White else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Compass Dial Visualization
        item {
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .border(
                        width = if (isFacingQibla) 3.dp else 1.5.dp,
                        color = if (isFacingQibla) EmeraldGreenPrimary else IslamicGold.copy(alpha = 0.6f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Background compass dial rotating with device azimuth
                val animatedCompassRotation by animateFloatAsState(
                    targetValue = -currentAzimuth,
                    animationSpec = spring(stiffness = 300f),
                    label = "compassRotation"
                )

                // Canvas for compass ticks and N, E, S, W markings
                val dialTickColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                val primaryColor = MaterialTheme.colorScheme.primary
                Canvas(
                    modifier = Modifier
                        .size(270.dp)
                        .rotate(animatedCompassRotation)
                ) {
                    val radius = size.minDimension / 2
                    val center = Offset(size.width / 2, size.height / 2)

                    // Draw 36 tick marks around circle
                    for (i in 0 until 36) {
                        val angleDeg = i * 10f
                        rotate(degrees = angleDeg, pivot = center) {
                            val isMajor = i % 9 == 0
                            val tickLength = if (isMajor) 14.dp.toPx() else 7.dp.toPx()
                            drawLine(
                                color = if (isMajor) primaryColor else dialTickColor,
                                start = Offset(center.x, center.y - radius + 8.dp.toPx()),
                                end = Offset(center.x, center.y - radius + 8.dp.toPx() + tickLength),
                                strokeWidth = if (isMajor) 3.dp.toPx() else 1.5.dp.toPx()
                            )
                        }
                    }
                }

                // Rotating Needle towards Qibla
                val needleRotation by animateFloatAsState(
                    targetValue = qiblaBearing - currentAzimuth,
                    animationSpec = spring(stiffness = 300f),
                    label = "needleRotation"
                )

                Column(
                    modifier = Modifier
                        .size(260.dp)
                        .rotate(needleRotation),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Kaaba indicator icon pointing up
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .size(44.dp),
                        border = androidx.compose.foundation.BorderStroke(2.dp, IslamicGold)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(id = R.drawable.qibla_kaaba),
                                contentDescription = "Kaaba Qibla",
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    // Needle pointer line
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .height(80.dp)
                            .background(
                                Brush.verticalGradient(
                                    listOf(IslamicGold, Color.Transparent)
                                )
                            )
                    )

                    // Bottom counterweight
                    Box(
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color.Gray.copy(alpha = 0.5f))
                    )
                }

                // Center pivot emblem
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.size(32.dp),
                    border = androidx.compose.foundation.BorderStroke(2.dp, IslamicGold),
                    shadowElevation = 4.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                        )
                    }
                }
            }
        }

        // Compass heading & manual simulation slider toggle
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (language == AppLanguage.BENGALI) "কম্পাস সেন্সর ও ক্রমাঙ্কন" else "Compass Sensor & Dial",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Text(
                            text = "${currentAzimuth.toInt()}°",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (language == AppLanguage.BENGALI) {
                            "ডিভাইসটি অনুভূমিকভাবে (ফ্ল্যাট) রাখুন। সিমুলেটরে বা সেন্সর অনুপলব্ধ থাকলে নিচের স্লাইডার দিয়ে টেস্ট করতে পারেন।"
                        } else {
                            "Keep your phone flat. If testing on an emulator or without magnetic sensor, drag the slider below to test rotation."
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Slider(
                        value = manualRotationSlider,
                        onValueChange = {
                            manualRotationSlider = it
                            useManualSlider = true
                        },
                        valueRange = 0f..360f,
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }

        // Calibration tip
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Calibration Info",
                        tint = IslamicGold,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = if (language == AppLanguage.BENGALI) {
                            "কম্পাসের সঠিকতার জন্য ফোনটিকে বাতাসে '8' (আট) আকৃতিতে কয়েকবার ঘোরান এবং ধাতব বা বৈদ্যুতিক চৌম্বকীয় বস্তু থেকে দূরে রাখুন।"
                        } else {
                            "Calibration tip: Wave your device gently in a figure-8 motion in the air. Keep away from magnetic cases, electronics, and heavy metal objects."
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
