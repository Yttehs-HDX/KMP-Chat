package com.shettydev.chatclient.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
actual fun dynamicColorScheme(): ColorScheme {
    val (lightScheme, darkScheme) = lightColorScheme() to darkColorScheme()
    return if (isSystemInDarkTheme()) darkScheme else lightScheme
}