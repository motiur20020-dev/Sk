package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = EmeraldGreenPrimaryDark,
    onPrimary = Color(0xFF032213),
    primaryContainer = Color(0xFF0E3E26),
    onPrimaryContainer = Color(0xFFA5F3C8),
    secondary = IslamicGoldLight,
    onSecondary = Color(0xFF382A00),
    secondaryContainer = Color(0xFF4C3B0D),
    onSecondaryContainer = Color(0xFFFFDF8D),
    tertiary = Color(0xFF4FD1C5),
    background = BackgroundDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondaryDark,
    outline = BorderDark
)

private val LightColorScheme = lightColorScheme(
    primary = EmeraldGreenPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD1E7DD),
    onPrimaryContainer = Color(0xFF073822),
    secondary = IslamicGold,
    onSecondary = Color(0xFF2C2200),
    secondaryContainer = Color(0xFFFFF2D1),
    onSecondaryContainer = Color(0xFF594500),
    tertiary = EmeraldGreenLight,
    background = BackgroundLight,
    onBackground = TextPrimaryLight,
    surface = SurfaceLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = TextSecondaryLight,
    outline = BorderLight
)

@Composable
fun NoorMuslimTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep branded Islamic palette by default
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
