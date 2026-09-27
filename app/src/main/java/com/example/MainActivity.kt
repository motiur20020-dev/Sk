package com.example

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.model.AppLanguage
import com.example.model.AppThemeMode
import com.example.ui.components.PermissionExplanationDialog
import com.example.ui.screens.BookmarksScreen
import com.example.ui.screens.CalendarScreen
import com.example.ui.screens.DuaScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MoreScreen
import com.example.ui.screens.PrayerScreen
import com.example.ui.screens.QiblaScreen
import com.example.ui.screens.QuranScreen
import com.example.ui.screens.RamadanScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SurahDetailScreen
import com.example.ui.theme.IslamicGold
import com.example.ui.theme.NoorMuslimTheme
import com.example.viewmodel.DuaViewModel
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.QuranViewModel
import com.example.viewmodel.RamadanViewModel
import com.example.viewmodel.TasbihViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val quranViewModel: QuranViewModel by viewModels()
    private val tasbihViewModel: TasbihViewModel by viewModels()
    private val duaViewModel: DuaViewModel by viewModels()
    private val ramadanViewModel: RamadanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val themeMode by mainViewModel.preferencesManager.themeMode.collectAsState()
            val isDark = when (themeMode) {
                AppThemeMode.SYSTEM -> isSystemInDarkTheme()
                AppThemeMode.LIGHT -> false
                AppThemeMode.DARK -> true
            }

            NoorMuslimTheme(darkTheme = isDark) {
                NoorMuslimApp(
                    mainViewModel = mainViewModel,
                    quranViewModel = quranViewModel,
                    tasbihViewModel = tasbihViewModel,
                    duaViewModel = duaViewModel,
                    ramadanViewModel = ramadanViewModel
                )
            }
        }
    }
}

data class NavTabItem(
    val id: String,
    val titleEn: String,
    val titleBn: String,
    val icon: ImageVector
)

@Composable
fun NoorMuslimApp(
    mainViewModel: MainViewModel,
    quranViewModel: QuranViewModel,
    tasbihViewModel: TasbihViewModel,
    duaViewModel: DuaViewModel,
    ramadanViewModel: RamadanViewModel
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val currentTab by mainViewModel.currentTab.collectAsState()
    val currentSubScreen by mainViewModel.currentSubScreen.collectAsState()
    val language by mainViewModel.preferencesManager.language.collectAsState()

    val showLocationExplanation by mainViewModel.showLocationExplanationDialog.collectAsState()
    val showNotifExplanation by mainViewModel.showNotificationExplanationDialog.collectAsState()

    // Location Permission Launcher
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
        if (fineGranted || coarseGranted) {
            mainViewModel.detectGpsLocation(
                onSuccess = { locName ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Location updated: $locName")
                    }
                },
                onError = { err ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(err)
                    }
                }
            )
        } else {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Location permission denied. You can select your city manually.")
            }
        }
    }

    // Notification Permission Launcher (Android 13+)
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Prayer and Adhan notifications enabled.")
            }
        } else {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Notifications permission was denied.")
            }
        }
    }

    // Check notification permission on launch for Android 13+
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notifGranted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!notifGranted) {
                mainViewModel.setShowNotificationExplanation(true)
            }
        }
    }

    fun requestGpsWithExplanation() {
        val fineGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (fineGranted) {
            mainViewModel.detectGpsLocation(
                onSuccess = { locName ->
                    coroutineScope.launch { snackbarHostState.showSnackbar("Location detected: $locName") }
                },
                onError = { err ->
                    coroutineScope.launch { snackbarHostState.showSnackbar(err) }
                }
            )
        } else {
            mainViewModel.setShowLocationExplanation(true)
        }
    }

    // Permission Explanation Dialogs
    if (showLocationExplanation) {
        PermissionExplanationDialog(
            title = if (language == AppLanguage.BENGALI) "অবস্থান ব্যবহারের অনুমতি" else "Location Permission",
            message = if (language == AppLanguage.BENGALI) {
                "আপনার এলাকার নামাযের সঠিক ওয়াক্ত ও ক্বিবলার সুনির্দিষ্ট দিক নির্ণয় করার জন্য ডিভাইসের অবস্থান (GPS) প্রয়োজন। আপনি চাইলে যেকোনো সময় ম্যানুয়ালি শহর নির্বাচন করতে পারেন।"
            } else {
                "Noor Muslim needs your location to calculate accurate prayer times and the precise direction of the Qibla for your coordinates. You can also select a city manually."
            },
            confirmText = if (language == AppLanguage.BENGALI) "অনুমতি দিন" else "Allow",
            dismissText = if (language == AppLanguage.BENGALI) "ম্যানুয়ালি বেছে নিব" else "Manual City",
            onConfirm = {
                mainViewModel.setShowLocationExplanation(false)
                locationPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            },
            onDismiss = {
                mainViewModel.setShowLocationExplanation(false)
            }
        )
    }

    if (showNotifExplanation) {
        PermissionExplanationDialog(
            title = if (language == AppLanguage.BENGALI) "আজান ও নামাযের নোটিফিকেশন" else "Prayer Alerts & Adhan",
            message = if (language == AppLanguage.BENGALI) {
                "পাঁচ ওয়াক্ত নামাযের সময় আপনাকে সময়মতো সতর্কবার্তা দিতে এবং আজানের ধ্বনি বাজাতে নোটিফিকেশন অনুমতি প্রয়োজন।"
            } else {
                "To notify you at the exact beginning of each prayer time and play the Adhan chime, notification permission is required."
            },
            confirmText = if (language == AppLanguage.BENGALI) "অনুমতি দিন" else "Enable Alerts",
            dismissText = if (language == AppLanguage.BENGALI) "পরে করব" else "Maybe Later",
            onConfirm = {
                mainViewModel.setShowNotificationExplanation(false)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            },
            onDismiss = {
                mainViewModel.setShowNotificationExplanation(false)
            }
        )
    }

    val tabs = listOf(
        NavTabItem("home", "Home", "হোম", Icons.Default.Home),
        NavTabItem("quran", "Quran", "কুরআন", Icons.Default.MenuBook),
        NavTabItem("prayer", "Prayer", "নামায", Icons.Default.AccessTime),
        NavTabItem("qibla", "Qibla", "ক্বিবলা", Icons.Default.Explore),
        NavTabItem("more", "More", "অন্যান্য", Icons.Default.Widgets)
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            // Show bottom bar only on root tabs (not on deep sub-screens like SurahDetail)
            if (currentSubScreen == null) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 6.dp,
                    modifier = Modifier.testTag("bottom_nav_bar")
                ) {
                    tabs.forEach { tab ->
                        val isSelected = currentTab == tab.id
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { mainViewModel.navigateToTab(tab.id) },
                            icon = {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = tab.titleEn,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = if (language == AppLanguage.BENGALI) tab.titleBn else tab.titleEn,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (currentSubScreen != null) {
                when (currentSubScreen) {
                    "surah_detail" -> {
                        SurahDetailScreen(
                            quranViewModel = quranViewModel,
                            mainViewModel = mainViewModel,
                            onBack = { mainViewModel.navigateBack() }
                        )
                    }
                    "tasbih" -> {
                        com.example.ui.screens.TasbihScreen(
                            tasbihViewModel = tasbihViewModel,
                            mainViewModel = mainViewModel,
                            onBack = { mainViewModel.navigateBack() }
                        )
                    }
                    "dua" -> {
                        DuaScreen(
                            duaViewModel = duaViewModel,
                            mainViewModel = mainViewModel,
                            onBack = { mainViewModel.navigateBack() }
                        )
                    }
                    "calendar" -> {
                        CalendarScreen(
                            mainViewModel = mainViewModel,
                            onBack = { mainViewModel.navigateBack() }
                        )
                    }
                    "ramadan" -> {
                        RamadanScreen(
                            ramadanViewModel = ramadanViewModel,
                            mainViewModel = mainViewModel,
                            onBack = { mainViewModel.navigateBack() }
                        )
                    }
                    "bookmarks" -> {
                        BookmarksScreen(
                            quranViewModel = quranViewModel,
                            duaViewModel = duaViewModel,
                            mainViewModel = mainViewModel,
                            onOpenSurah = { surahNum ->
                                quranViewModel.openSurah(surahNum)
                                mainViewModel.openSubScreen("surah_detail", surahNum)
                            },
                            onBack = { mainViewModel.navigateBack() }
                        )
                    }
                    "settings" -> {
                        SettingsScreen(
                            mainViewModel = mainViewModel,
                            onBack = { mainViewModel.navigateBack() }
                        )
                    }
                }
            } else {
                when (currentTab) {
                    "home" -> {
                        HomeScreen(
                            mainViewModel = mainViewModel,
                            onNavigateTab = { tab -> mainViewModel.navigateToTab(tab) },
                            onOpenSubScreen = { screen -> mainViewModel.openSubScreen(screen) },
                            onRequestGps = { requestGpsWithExplanation() }
                        )
                    }
                    "quran" -> {
                        QuranScreen(
                            quranViewModel = quranViewModel,
                            mainViewModel = mainViewModel,
                            onOpenSurah = { surahNum ->
                                quranViewModel.openSurah(surahNum)
                                mainViewModel.openSubScreen("surah_detail", surahNum)
                            }
                        )
                    }
                    "prayer" -> {
                        PrayerScreen(
                            mainViewModel = mainViewModel,
                            onRequestGps = { requestGpsWithExplanation() }
                        )
                    }
                    "qibla" -> {
                        QiblaScreen(mainViewModel = mainViewModel)
                    }
                    "more" -> {
                        MoreScreen(
                            mainViewModel = mainViewModel,
                            onOpenSubScreen = { screen -> mainViewModel.openSubScreen(screen) }
                        )
                    }
                }
            }
        }
    }
}
