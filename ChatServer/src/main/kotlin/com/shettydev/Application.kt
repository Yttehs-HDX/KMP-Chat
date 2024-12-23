package com.shettydev

import com.shettydev.routing.configureRouting
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    installWebSockets()
    configureRouting()
}