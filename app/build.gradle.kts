/*
 * Copyright (c) 2020 Plaid Technologies, Inc. <support@plaid.com>
 */

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "com.plaid.linksample"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.plaid.linksample"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("com.plaid.link:sdk-core:5.0.0")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.10.0")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")
}
