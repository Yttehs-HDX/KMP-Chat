package com.shettydev.chatclient.entity

import kotlinx.serialization.Serializable

@Serializable
data class ChatMsg(
    val userId: Int,
    val userName: String,
    val content: String,
)