package com.shettydev.chatclient.ui

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.shettydev.chatclient.MainApplication

@Composable
actual fun dynamicColorScheme(): ColorScheme {
    val (lightScheme, darkScheme) = when {
        Build.VERSION.SDK_INT < Build.VERSION_CODES.S -> {
            lightColorScheme() to darkColorScheme()
        }

        else -> {
            val context = MainApplication.Context
            dynamicLightColorScheme(context) to dynamicDarkColorScheme(context)
        }
    }

    return if (isSystemInDarkTheme()) darkScheme else lightScheme
}