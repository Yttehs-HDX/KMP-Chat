package com.shettydev.chatclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.shettydev.chatclient.ui.dynamicColorScheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SystemBarStyle()
            App()
        }
    }
}

@Suppress("DEPRECATION")
@Composable
fun SystemBarStyle() {
    val systemUiController = rememberSystemUiController()

    val colorScheme = dynamicColorScheme()
    val statusBarColor = colorScheme.background
    val navigationBarColor = colorScheme.background
    val isDarkMode = isSystemInDarkTheme()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = !isDarkMode,
        )
        systemUiController.setNavigationBarColor(
            color = navigationBarColor,
            darkIcons = !isDarkMode,
        )
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}