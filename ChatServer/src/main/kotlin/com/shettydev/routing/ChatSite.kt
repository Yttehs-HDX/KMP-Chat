package com.shettydev.routing

import com.shettydev.entity.ChatClient
import com.shettydev.entity.ChatMsg
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

private val lastId = AtomicInteger(0)
private val clients = ConcurrentHashMap<Int, ChatClient>()

fun Routing.chatSite() {
    webSocket("/chat") {
        val clientId = lastId.incrementAndGet()
        val client = ChatClient(this, "Nameless", clientId)
        clients[clientId] = client

        try {
            while (true) {
                val frame = incoming.receiveCatching().getOrNull() ?: break
                when (frame) {
                    is Frame.Text -> {
                        when (val rawText = frame.readText().trim()) {
                            // "/name NewName" command to change the username
                            rawText.startsWith("/name ").toString() -> {
                                val newName = rawText
                                    .removePrefix("/name ")
                                    .trim()
                                if (newName.isNotBlank()) {
                                    client.userName = newName
                                }
                            }

                            // send a message to all clients
                            else -> {
                                val chatMessage = ChatMsg(
                                    userId = client.id,
                                    userName = client.userName,
                                    content = rawText
                                )
                                broadcastMessage(chatMessage)
                            }
                        }
                    }

                    else -> continue
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            clients.remove(client.id)
        }
    }
}

private suspend fun broadcastMessage(msg: ChatMsg) {
    val json = Json { prettyPrint = false }
    val textToSend = json.encodeToString(msg)
    println(textToSend)

    clients.values.forEach { client ->
        client.session.outgoing.send(Frame.Text(textToSend))
    }
}