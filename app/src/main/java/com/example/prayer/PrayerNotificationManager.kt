package com.example.prayer

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import com.example.R
import com.example.data.local.PreferencesManager
import com.example.model.PrayerName
import java.util.Calendar

class PrayerAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val prayerName = intent.getStringExtra("PRAYER_NAME") ?: "Salah"
        val prayerBn = intent.getStringExtra("PRAYER_BN") ?: "নামায"
        val isAdhan = intent.getBooleanExtra("PLAY_ADHAN", true)

        PrayerNotificationManager.showPrayerNotification(context, prayerName, prayerBn, isAdhan)
    }
}

object PrayerNotificationManager {
    const val CHANNEL_ID_PRAYER = "noor_muslim_prayer_channel"
    const val CHANNEL_ID_REMINDERS = "noor_muslim_reminders_channel"

    fun initNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttr = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                .build()

            val prayerChannel = NotificationChannel(
                CHANNEL_ID_PRAYER,
                "Prayer & Adhan Times",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Daily Adhan and prayer time reminders"
                enableVibration(true)
                setSound(soundUri, audioAttr)
            }

            val reminderChannel = NotificationChannel(
                CHANNEL_ID_REMINDERS,
                "Daily Islamic Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Morning/Evening Azkar and Quran reading reminders"
                enableVibration(true)
            }

            notificationManager.createNotificationChannel(prayerChannel)
            notificationManager.createNotificationChannel(reminderChannel)
        }
    }

    fun showPrayerNotification(context: Context, prayerEn: String, prayerBn: String, playAdhan: Boolean) {
        initNotificationChannels(context)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val openIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val title = "Hayya 'alas-Salah! ($prayerEn)"
        val content = "It is time for $prayerEn ($prayerBn) prayer. Come to prayer, come to success."

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID_PRAYER)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle(title)
            .setContentText(content)
            .setSubText("Noor Muslim")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setVibrate(longArrayOf(0, 500, 200, 500))
            .setContentIntent(pendingIntent)

        val notifId = prayerEn.hashCode()
        notificationManager.notify(notifId, builder.build())
    }

    fun schedulePrayerAlarms(context: Context, preferencesManager: PreferencesManager) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val isMasterEnabled = preferencesManager.masterNotificationEnabled.value
        if (!isMasterEnabled) return

        val location = preferencesManager.userLocation.value
        val calcMethod = preferencesManager.calculationMethod.value
        val asrMethod = preferencesManager.asrMethod.value

        val schedule = PrayerTimeCalculator.buildPrayerSchedule(
            date = Calendar.getInstance().time,
            location = location,
            calcMethod = calcMethod,
            asrMethod = asrMethod,
            hijriDateString = "",
            cityName = location.cityNameEn
        )

        val now = System.currentTimeMillis()
        schedule.prayers.forEach { item ->
            if (item.prayer == PrayerName.SUNRISE) return@forEach
            val enabled = when (item.prayer) {
                PrayerName.FAJR -> preferencesManager.fajrNotif.value
                PrayerName.DHUHR -> preferencesManager.dhuhrNotif.value
                PrayerName.ASR -> preferencesManager.asrNotif.value
                PrayerName.MAGHRIB -> preferencesManager.maghribNotif.value
                PrayerName.ISHA -> preferencesManager.ishaNotif.value
                else -> false
            }

            if (enabled && item.timestampMillis > now) {
                val intent = Intent(context, PrayerAlarmReceiver::class.java).apply {
                    putExtra("PRAYER_NAME", item.nameEn)
                    putExtra("PRAYER_BN", item.nameBn)
                    putExtra("PLAY_ADHAN", preferencesManager.masterAdhanEnabled.value)
                }

                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    item.prayer.ordinal,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            item.timestampMillis,
                            pendingIntent
                        )
                    } else {
                        alarmManager.setExact(
                            AlarmManager.RTC_WAKEUP,
                            item.timestampMillis,
                            pendingIntent
                        )
                    }
                } catch (e: SecurityException) {
                    // Fallback to standard set if exact alarm permission restricted
                    alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        item.timestampMillis,
                        pendingIntent
                    )
                }
            }
        }
    }
}
