/*
 * Copyright (c) 2020 Plaid Technologies, Inc. <support@plaid.com>
 */

package com.cibc.us.app.android

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "FCMService"
        private const val CHANNEL_ID = "plaid_notifications"
        private const val NOTIFICATION_ID = 1
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            handleDataMessage(remoteMessage.data)
        }

        // Check if message contains a notification payload
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            showNotification(it.title, it.body)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")
        
        // Send token to your server
        sendRegistrationToServer(token)
    }

    private fun handleDataMessage(data: Map<String, String>) {
        // Handle data payload of FCM messages here
        // This is where you can process custom data sent from your server
        
        val title = data["title"] ?: "Plaid Notification"
        val body = data["body"] ?: "You have a new notification"
        val action = data["action"] // Custom action type
        
        when (action) {
            "link_update" -> {
                // Handle Plaid Link update notifications
                Log.d(TAG, "Handling link update notification")
            }
            "account_verification" -> {
                // Handle account verification notifications
                Log.d(TAG, "Handling account verification notification")
            }
            else -> {
                // Handle generic notifications
                Log.d(TAG, "Handling generic notification")
            }
        }
        
        showNotification(title, body)
    }

    private fun showNotification(title: String?, body: String?) {
        createNotificationChannel()

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.plaid_icon)
            .setContentTitle(title ?: "Plaid Notification")
            .setContentText(body ?: "You have a new notification")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Plaid Notifications"
            val descriptionText = "Notifications for Plaid Link updates and account information"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendRegistrationToServer(token: String) {
        // TODO: Implement this method to send token to your server
        Log.d(TAG, "Sending registration token to server: $token")
        
        // Example: Send token to your backend server
        // This is where you would make an API call to register the device token
        // with your server so it can send push notifications to this device
        
        // Example implementation:
        // ApiClient.registerDeviceToken(token)
    }
}
