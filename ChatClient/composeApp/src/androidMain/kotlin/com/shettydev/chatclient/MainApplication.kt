package com.shettydev.chatclient

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class MainApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var appContext: Context
        val Context: Context
            get() = appContext
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}