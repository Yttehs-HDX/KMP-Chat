package com.shettydev.chatclient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<String>>(emptyList())
    val messages: StateFlow<List<String>>
        get() = _messages

    private val client = HttpClient { install(WebSockets) }
    private lateinit var webSocketSession: DefaultWebSocketSession

    fun connect(url: Url) {
        viewModelScope.launch {
            try {
                if (::webSocketSession.isInitialized && webSocketSession.isActive) {
                    webSocketSession.close(
                        CloseReason(
                            CloseReason.Codes.NORMAL,
                            "Reconnecting..."
                        )
                    )
                }

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
                                _messages.value += receivedText
                            }

                            else -> continue
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
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
}