package com.shettydev.chatclient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shettydev.chatclient.entity.ChatMsg
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class ChatViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<ChatMsgUiState>>(emptyList())
    val messages: StateFlow<List<ChatMsgUiState>>
        get() = _messages

    private val client = HttpClient { install(WebSockets) }
    private lateinit var webSocketSession: DefaultWebSocketSession

    fun connect(url: Url) {
        viewModelScope.launch {
            tryCloseOldSession()

            try {
                webSocketSession = client.webSocketSession(
                    method = HttpMethod.Get,
                    path = url.encodedPath,
                    host = url.host,
                    port = url.port,
                )

                launch {
                    for (frame in webSocketSession.incoming) {
                        when (frame) {
                            is Frame.Text -> {
                                val receivedText = frame.readText()

                                // handle json
                                val json = Json { prettyPrint = false }
                                val msg = json.decodeFromString<ChatMsg>(receivedText)
                                val msgUiState = msg.toUiState()

                                _messages.value += msgUiState
                            }

                            else -> continue
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                client.close()
            }
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            try {
                if (::webSocketSession.isInitialized && webSocketSession.isActive) {
                    webSocketSession.outgoing.send(Frame.Text(message))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun tryCloseOldSession() {
        if (::webSocketSession.isInitialized && webSocketSession.isActive) {
            webSocketSession.close(
                CloseReason(CloseReason.Codes.NORMAL, "Reconnecting...")
            )
        }
    }
}

data class ChatMsgUiState(
    val username: String,
    val message: String,
)

fun ChatMsg.toUiState(): ChatMsgUiState {
    return ChatMsgUiState(
        username = "$userName ($userId)",
        message = content,
    )
}