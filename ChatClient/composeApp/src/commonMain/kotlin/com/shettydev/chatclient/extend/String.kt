package com.shettydev.chatclient.extend

fun String.isWsUrl(): Boolean {
    return this.startsWith("ws://") || this.startsWith("wss://")
}