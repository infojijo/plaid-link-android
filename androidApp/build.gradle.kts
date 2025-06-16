/*
 * Copyright (c) 2020 Plaid Technologies, Inc. <support@plaid.com>
 */

apply(plugin = "com.android.application")
apply(plugin = "org.jetbrains.kotlin.android")
apply(plugin = "com.google.gms.google-services")

// Task to copy the appropriate google-services.json based on build type
tasks.register<Copy>("copyGoogleServicesDebug") {
    description = "Copies the debug google-services.json file"
    from("${rootProject.projectDir}/firebase-configs/google-services-debug.json")
    into("${projectDir}")
    rename("google-services-debug.json", "google-services.json")
}

tasks.register<Copy>("copyGoogleServicesRelease") {
    description = "Copies the release google-services.json file"
    from("${rootProject.projectDir}/firebase-configs/google-services-release.json")
    into("${projectDir}")
    rename("google-services-release.json", "google-services.json")
}

// Task to clean up google-services.json
tasks.register<Delete>("cleanGoogleServices") {
    description = "Cleans up the google-services.json file"
    delete("${projectDir}/google-services.json")
}

// Hook into the build process to copy the appropriate google-services.json
afterEvaluate {
    // Clean task should also clean google-services.json
    tasks.named("clean") {
        dependsOn("cleanGoogleServices")
    }
    
    // Hook into preBuild tasks to copy the appropriate config
    tasks.named("preBuild") {
        dependsOn("copyGoogleServicesDebug")
    }
    
    // Make Google Services tasks depend on copy tasks
    tasks.matching { it.name.contains("processDebugGoogleServices") }.configureEach {
        dependsOn("copyGoogleServicesDebug")
    }
    
    tasks.matching { it.name.contains("processReleaseGoogleServices") }.configureEach {
        dependsOn("copyGoogleServicesRelease")
    }
    
    // For release builds, override with release config
    tasks.matching { it.name.contains("Release") && it.name.contains("preBuild") }.configureEach {
        dependsOn("copyGoogleServicesRelease")
        mustRunAfter("copyGoogleServicesDebug")
    }
}

configure<com.android.build.gradle.AppExtension> {
    namespace = "com.cibc.us.app.android"
    compileSdkVersion(35)

    defaultConfig {
        applicationId = "com.cibc.us.app.android"
        minSdkVersion(30)
        targetSdkVersion(35)
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
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
    
    // Firebase dependencies for push notifications
    add("implementation", platform("com.google.firebase:firebase-bom:32.7.0"))
    add("implementation", "com.google.firebase:firebase-messaging-ktx")
    add("implementation", "com.google.firebase:firebase-analytics-ktx")
}
