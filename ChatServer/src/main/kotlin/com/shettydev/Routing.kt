package com.shettydev

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun Application.configureRouting() {
    routing {
        mainSite()
    }
}

fun Routing.mainSite() {
    get("/") {
        call.respondHtml {
            head {
                title { +"Chat Server" }
            }
            body {
                h1 { +"Chat Server" }
                p {
                    +"Powered by "
                    a {
                        href = "https://ktor.io"
                        +"Ktor"
                    }
                }
            }
        }
    }
}