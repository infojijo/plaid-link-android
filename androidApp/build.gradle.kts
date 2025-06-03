/*
 * Copyright (c) 2020 Plaid Technologies, Inc. <support@plaid.com>
 */

apply(plugin = "com.android.application")
apply(plugin = "org.jetbrains.kotlin.android")

configure<com.android.build.gradle.AppExtension> {
    namespace = "com.plaid.linksample"
    compileSdkVersion(35)

    defaultConfig {
        applicationId = "com.plaid.linksample"
        minSdkVersion(30)
        targetSdkVersion(35)
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    add("implementation", project(":shared"))
    
    add("implementation", "androidx.appcompat:appcompat:1.6.1")
    add("implementation", "androidx.core:core-ktx:1.16.0")
    add("implementation", "androidx.constraintlayout:constraintlayout:2.1.4")
    add("implementation", "com.google.android.material:material:1.10.0")
    add("implementation", "com.plaid.link:sdk-core:5.0.0")
    add("implementation", "io.reactivex.rxjava3:rxandroid:3.0.0")
    add("implementation", "com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    add("implementation", "com.squareup.retrofit2:retrofit:2.9.0")
    add("implementation", "com.squareup.retrofit2:converter-gson:2.9.0")
    add("implementation", "com.google.code.gson:gson:2.10.1")
    add("implementation", "io.reactivex.rxjava3:rxjava:3.1.8")
}
