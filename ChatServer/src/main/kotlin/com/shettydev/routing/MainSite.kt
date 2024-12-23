package com.shettydev.routing

import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.*

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