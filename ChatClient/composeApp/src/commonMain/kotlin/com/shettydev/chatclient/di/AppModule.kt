package com.shettydev.chatclient.di

import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

val koin = GlobalContext.getOrNull() ?: run {
    initKoin()
    GlobalContext.get()
}

private fun initKoin() {
    startKoin {
        modules(
            module {
                // add dependencies here

            }
        )
    }
}