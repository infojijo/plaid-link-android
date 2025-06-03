package com.plaid.linksample

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}! This is a Kotlin Multiplatform project for Plaid Link."
    }
}
