package com.shettydev.chatclient

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform