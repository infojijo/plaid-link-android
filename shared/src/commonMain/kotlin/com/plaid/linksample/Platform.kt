package com.plaid.linksample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
