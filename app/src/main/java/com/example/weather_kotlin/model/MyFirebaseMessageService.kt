package com.example.weather_kotlin.model

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.weather_kotlin.R
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val TAG = "MyFirebaseMessageServ"
const val NOTIFICATION_ID = 42
const val CHANNEL_ID = "Default"

class MyFirebaseMessageService : FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG, "onMessageReceived $message")
        super.onMessageReceived(message)

        val data = message.data

       val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_history_edu_24)
            .setContentTitle("Hello")



        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.getNotificationChannel(CHANNEL_ID)


        notificationManager.createNotificationChannel(NotificationChannel(
            CHANNEL_ID,
            "MyChannel",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "message"
        }
        )
        notificationBuilder.setChannelId(CHANNEL_ID)

        notificationManager.notify(42, notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "onNewToken $token")
        super.onNewToken(token)
    }


}