package com.shettydev.entity

import io.ktor.websocket.*

data class ChatClient(
    val session: DefaultWebSocketSession,
    var userName: String,
    val id: Int,
)