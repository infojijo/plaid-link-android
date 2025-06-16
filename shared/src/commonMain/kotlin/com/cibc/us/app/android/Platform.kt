package com.cibc.us.app.android

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
