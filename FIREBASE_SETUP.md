# Firebase Push Notifications Setup

This document describes the Firebase Cloud Messaging (FCM) setup for push notifications in the CIBC US Android app.

## Files Added/Modified

### 1. Firebase Configuration
- **`androidApp/google-services.json`**: Sample Firebase configuration file
  - Contains project configuration for `com.cibc.us.app.android` package
  - **IMPORTANT**: Replace with your actual Firebase project configuration

### 2. Build Configuration
- **`build.gradle.kts`**: Added Google Services plugin classpath
- **`androidApp/build.gradle.kts`**: 
  - Applied Google Services plugin
  - Added Firebase BOM and messaging dependencies

### 3. Firebase Messaging Service
- **`androidApp/src/main/java/com/cibc/us/app/android/FirebaseMessagingService.kt`**
  - Handles incoming push notifications
  - Processes data payloads and notification payloads
  - Creates notification channels for Android 8.0+
  - Supports custom actions (link_update, account_verification)

### 4. Application Class
- **`androidApp/src/main/java/com/cibc/us/app/android/LinkSampleApplication.kt`**
  - Initializes Firebase on app startup
  - Retrieves and logs FCM registration token
  - Ready for token registration with backend server

### 5. Android Manifest
- **`androidApp/src/main/AndroidManifest.xml`**
  - Registered FirebaseMessagingService
  - Configured default notification icon and color
  - Set default notification channel

### 6. ProGuard Rules
- **`androidApp/proguard-rules.pro`**: Added comprehensive Firebase ProGuard rules
- **`shared/consumer-rules.pro`**: Updated with Firebase rules for shared module

## Setup Instructions

### 1. Replace Sample Configuration
Replace the sample `google-services.json` with your actual Firebase project configuration:

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project or select existing project
3. Add Android app with package name: `com.cibc.us.app.android`
4. Download the `google-services.json` file
5. Replace `androidApp/google-services.json` with your downloaded file

### 2. Firebase Project Configuration
In your Firebase project:

1. **Enable Cloud Messaging**:
   - Go to Project Settings > Cloud Messaging
   - Note your Server Key for backend integration

2. **Configure Notification Settings**:
   - Set up notification channels if needed
   - Configure notification appearance

### 3. Backend Integration
Update your backend server to:

1. **Store FCM Tokens**:
   ```kotlin
   // In FirebaseMessagingService.kt - sendRegistrationToServer()
   // Send token to your backend API
   ```

2. **Send Push Notifications**:
   ```json
   {
     "to": "FCM_TOKEN",
     "notification": {
       "title": "Plaid Link Update",
       "body": "Your account verification is complete"
     },
     "data": {
       "action": "link_update",
       "account_id": "12345"
     }
   }
   ```

## Notification Types

### 1. Link Update Notifications
```json
{
  "data": {
    "action": "link_update",
    "title": "Account Updated",
    "body": "Your Plaid Link connection has been updated"
  }
}
```

### 2. Account Verification Notifications
```json
{
  "data": {
    "action": "account_verification",
    "title": "Verification Complete",
    "body": "Your account has been successfully verified"
  }
}
```

### 3. Generic Notifications
```json
{
  "notification": {
    "title": "CIBC Notification",
    "body": "You have a new message"
  }
}
```

## Testing Push Notifications

### 1. Using Firebase Console
1. Go to Firebase Console > Cloud Messaging
2. Click "Send your first message"
3. Enter notification details
4. Select your app
5. Send test message

### 2. Using FCM API
```bash
curl -X POST https://fcm.googleapis.com/fcm/send \
  -H "Authorization: key=YOUR_SERVER_KEY" \
  -H "Content-Type: application/json" \
  -d '{
    "to": "FCM_TOKEN",
    "notification": {
      "title": "Test Notification",
      "body": "This is a test message"
    }
  }'
```

### 3. Check Logs
Monitor Android logs for FCM token and message handling:
```bash
adb logcat | grep -E "(FCMService|LinkSampleApp)"
```

## Security Considerations

1. **Secure Token Storage**: Store FCM tokens securely on your backend
2. **Validate Payloads**: Always validate notification payloads on the backend
3. **User Permissions**: Request notification permissions appropriately
4. **Data Privacy**: Don't send sensitive data in notification payloads

## Troubleshooting

### Common Issues

1. **google-services.json not found**:
   - Ensure file is in `androidApp/` directory
   - Verify package name matches in Firebase console

2. **Token not received**:
   - Check internet connection
   - Verify Google Play Services is installed
   - Check app permissions

3. **Notifications not showing**:
   - Verify notification permissions
   - Check notification channel settings
   - Ensure app is not in battery optimization

### Debug Commands
```bash
# Check FCM token
adb logcat | grep "FCM Registration Token"

# Monitor notification handling
adb logcat | grep "FCMService"

# Check Google Play Services
adb shell dumpsys package com.google.android.gms | grep version
```

## Dependencies Added

```kotlin
// Firebase BOM for version management
implementation(platform("com.google.firebase:firebase-bom:32.7.0"))

// Firebase Cloud Messaging
implementation("com.google.firebase:firebase-messaging-ktx")

// Firebase Analytics (optional)
implementation("com.google.firebase:firebase-analytics-ktx")
```

## Next Steps

1. Replace sample `google-services.json` with your project configuration
2. Implement backend API for token registration
3. Set up notification sending from your backend
4. Test push notifications end-to-end
5. Implement notification click handling if needed
6. Add notification preferences in app settings
