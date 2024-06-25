package com.example.facetimeclonecompose.framework.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.facetimeclonecompose.R
import com.example.facetimeclonecompose.domain.usecases.UpdateUserFcmTokenUseCase
import com.example.facetimeclonecompose.framework.services.Constants.CALL_INVITATION
import com.example.facetimeclonecompose.framework.services.Constants.CALL_TYPE
import com.example.facetimeclonecompose.framework.services.Constants.CHANNEL_ID
import com.example.facetimeclonecompose.framework.services.Constants.NOTIFICATION_ID
import com.example.facetimeclonecompose.framework.services.Constants.RECEIVED_MESSAGE_TYPE
import com.example.facetimeclonecompose.framework.services.Constants.ROOM_AUTHOR
import com.example.facetimeclonecompose.framework.services.Constants.ROOM_ID
import com.example.facetimeclonecompose.presentation.utilities.Constants
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FCMService @Inject constructor() : FirebaseMessagingService() {
    @Inject
    lateinit var updateUserFcmTokenUseCase: UpdateUserFcmTokenUseCase
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data[RECEIVED_MESSAGE_TYPE].toString() == CALL_INVITATION) {  // TYPE = [CALL, RESPONSE]
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                applicationContext.startActivity(showCallScreen(remoteMessage))
            } else {
                powerOptimization()
                showCallInvitationNotification(remoteMessage)
            }
        } //else if (remoteMessage.data[RECEIVED_MESSAGE_TYPE].toString() == RESPONSE_CALL) {
//            with(NotificationManagerCompat.from(this)) {
//                cancel(NOTIFICATION_ID)
//            }
//            val intent: Intent = Intent("response")
//            intent.putExtra("response", remoteMessage.data.get("response").toString())
//            intent.putExtra("roomKey", remoteMessage.data.get("key").toString())
//            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
        //}
    }

    private fun showCallScreen(remoteMessage: RemoteMessage):Intent {
        val incomingCallIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("${Constants.DEEP_LINK_BASE_URL}/callId=${remoteMessage.data[ROOM_ID].toString()}/callAuthor=${remoteMessage.data[ROOM_AUTHOR].toString()}/roomType=${remoteMessage.data[CALL_TYPE].toString()}")
//            Uri.parse("${Constants.DEEP_LINK_BASE_URL}/callId=${remoteMessage.data[ROOM_KEY].toString()}/callAuthor=${remoteMessage.data[ROOM_NAME].toString()}/roomType=${{remoteMessage.data[CALL_TYPE].toString()}}")
        ).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        return incomingCallIntent
    }

    private fun powerOptimization(){
        val powerManager: PowerManager = getSystemService(POWER_SERVICE) as PowerManager;

        if (!powerManager.isInteractive) {
            var wl: PowerManager.WakeLock = powerManager.newWakeLock(
                PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.ON_AFTER_RELEASE,
                "FaceTimeClone:tag"
            )
            wl.acquire(30000);
            var wl_cpu: PowerManager.WakeLock = powerManager.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK,
                "FaceTimeCloneCompose:tag"
            );
            wl_cpu.acquire(30000);
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showCallInvitationNotification(remoteMessage: RemoteMessage){
        val channel = NotificationChannel(
            CHANNEL_ID,
            "FaceTime",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "FaceTime Call"
            setSound(
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE),
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
            )
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            vibrationPattern = longArrayOf(500, 1000)
            enableVibration(true)
            enableLights(true)
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        val incomingCallIntent: Intent = showCallScreen(remoteMessage)

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
                R.drawable.ic_baseline_phone_24, "Join", getActivity(
                    this,
                    0,
                    incomingCallIntent,
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT else FLAG_UPDATE_CURRENT
                )
            )
            .setContentIntent(
                getActivity(
                    applicationContext,
                    0,
                    incomingCallIntent,
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) FLAG_IMMUTABLE else FLAG_UPDATE_CURRENT
                )
            )
        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        scope.launch {
            try {
                updateUserFcmTokenUseCase(p0);
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}