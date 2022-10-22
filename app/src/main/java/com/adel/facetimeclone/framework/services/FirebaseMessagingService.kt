package com.adel.facetimeclone.domain.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.adel.facetimeclone.R
import com.adel.facetimeclone.presentation.incomingCallScreen.IncomingActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FirebaseMessagingService : FirebaseMessagingService() {
    val CHANNEL_ID = "facetimeid"
    val NOTIFICATION_ID = 1221254588
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.get("type").toString() == "call") {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                val incomingActivityIntent: Intent = Intent(this, IncomingActivity::class.java)
                incomingActivityIntent.putExtra("response", remoteMessage.data.get("response").toString())
                incomingActivityIntent.putExtra("name", remoteMessage.data.get("name").toString())
                incomingActivityIntent.putExtra("key", remoteMessage.data.get("key").toString())
                incomingActivityIntent.putExtra("author", remoteMessage.data.get("author").toString())
                incomingActivityIntent.putExtra("type", "call")
                applicationContext.startActivity(incomingActivityIntent)
            } else {
                var powerManager: PowerManager = getSystemService(POWER_SERVICE) as PowerManager;

                if (!powerManager.isInteractive) {
                    var wl: PowerManager.WakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.ON_AFTER_RELEASE, "FaceTimeClone:tag")
                    wl.acquire(30000);
                    var wl_cpu: PowerManager.WakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "FaceTimeClone:tag");
                    wl_cpu.acquire(30000);
                }
                val channel = NotificationChannel(
                        CHANNEL_ID,
                        "FaceTime",
                        NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "FaceTime Call"
                    setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE),
                            AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                    .setUsage(AudioAttributes.USAGE_ALARM)
                                    .build())
                    lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                    vibrationPattern = longArrayOf(500, 1000)
                    enableVibration(true)
                    enableLights(true)
                }
                val notificationManager: NotificationManager =
                        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
                val incomingActivityIntent: Intent = Intent(this, IncomingActivity::class.java)
                incomingActivityIntent.putExtra("response", remoteMessage.data.get("response").toString())
                incomingActivityIntent.putExtra("name", remoteMessage.data.get("name").toString())
                incomingActivityIntent.putExtra("key", remoteMessage.data.get("key").toString())
                incomingActivityIntent.putExtra("author", remoteMessage.data.get("authorUID").toString())
                incomingActivityIntent.putExtra("type", remoteMessage.data.get("type").toString())
                val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("FaceTime call from ${remoteMessage.data.get("name")}")
                        .setContentText("FaceTime call")
                        .setTimeoutAfter((30 * 1000L))
                        .setAutoCancel(true)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE))
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setVibrate(longArrayOf(500, 1000))
                        .setLights(Color.WHITE, 3000, 3000)
                        .addAction(
                                R.drawable.ic_baseline_phone_24, "Join", PendingIntent.getActivity(
                                this,
                                0,
                                incomingActivityIntent,
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) FLAG_MUTABLE else FLAG_UPDATE_CURRENT
                        )
                        )
                        .setContentIntent(
                                PendingIntent.getActivity(
                                        this,
                                        0,
                                        incomingActivityIntent,
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) FLAG_MUTABLE else FLAG_UPDATE_CURRENT
                                )
                        )
                with(NotificationManagerCompat.from(this)) {
                    notify(NOTIFICATION_ID, builder.build())
                }
            }
        } else if (remoteMessage.data.get("type").toString() == "response") {
            with(NotificationManagerCompat.from(this)) {
                cancel(NOTIFICATION_ID)
            }
            val intent: Intent = Intent("response")
            intent.putExtra("response", remoteMessage.data.get("response").toString())
            intent.putExtra("roomKey", remoteMessage.data.get("key").toString())
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
        }
    }
}