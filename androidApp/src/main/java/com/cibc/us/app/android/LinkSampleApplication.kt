/*
 * Copyright (c) 2020 Plaid Technologies, Inc. <support@plaid.com>
 */

package com.cibc.us.app.android

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.plaid.link.Plaid

@Suppress("Unused")
class LinkSampleApplication : Application() {

  companion object {
    private const val TAG = "LinkSampleApp"
  }

  override fun onCreate() {
    super.onCreate()

    // Initialize Firebase
    FirebaseApp.initializeApp(this)
    
    // Get Firebase Messaging token
    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
      if (!task.isSuccessful) {
        Log.w(TAG, "Fetching FCM registration token failed", task.exception)
        return@addOnCompleteListener
      }

      // Get new FCM registration token
      val token = task.result
      Log.d(TAG, "FCM Registration Token: $token")
      
      // TODO: Send token to your server
      // This is where you would typically send the token to your backend
      // so it can send push notifications to this device
    }
  }
}
