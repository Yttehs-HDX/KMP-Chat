package com.shettydev.routing

import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.util.Collections
import java.util.concurrent.atomic.AtomicInteger

class ChatClient(val session: DefaultWebSocketSession) {
    companion object {
        val LastId = AtomicInteger(0)
    }

    val id = LastId.getAndIncrement()
    val name = "user$id"
}

fun Routing.chatSite() {
    val clients = Collections.synchronizedSet(mutableSetOf<ChatClient>())

    webSocket("/chat") {
        val client = ChatClient(this)
        clients += client

        try {
            while (true) {
                when (val frame = incoming.receive()) {
                    is Frame.Text -> {
                        val rawText = frame.readText()
                        val text = "${client.name} (id = ${client.id}): $rawText"
                        val textToSend = Frame.Text(text)
                        clients.forEach {
                            it.session.outgoing.send(textToSend)
                        }
                    }

                    else -> {}
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            clients -= client
        }
    }
}