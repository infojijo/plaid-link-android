/*
 * Copyright (c) 2020 Plaid Technologies, Inc. <support@plaid.com>
 */

apply(plugin = "org.jetbrains.kotlin.multiplatform")
apply(plugin = "com.android.library")

configure<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension> {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    
    // iOS targets commented out for now - can be enabled when Xcode is properly configured
    /*
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }
    */

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Common dependencies that work across all platforms
            }
        }
        
        val androidMain by getting {
            dependencies {
                implementation("com.plaid.link:sdk-core:5.0.0")
                implementation("io.reactivex.rxjava3:rxandroid:3.0.0")
                implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
                implementation("com.squareup.retrofit2:retrofit:2.9.0")
                implementation("com.squareup.retrofit2:converter-gson:2.9.0")
                implementation("com.google.code.gson:gson:2.10.1")
                implementation("io.reactivex.rxjava3:rxjava:3.1.8")
            }
        }
        
        // iOS source sets commented out for now - can be enabled when Xcode is properly configured
        /*
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                // iOS-specific dependencies can be added here
            }
        }
        */
    }
}

configure<com.android.build.gradle.LibraryExtension> {
    namespace = "com.plaid.linksample.shared"
    compileSdkVersion(35)
    
    defaultConfig {
        minSdkVersion(30)
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
