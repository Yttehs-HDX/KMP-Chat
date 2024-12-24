package com.shettydev.chatclient

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.shettydev.chatclient.ui.AppTopBar
import com.shettydev.chatclient.ui.MainPage
import com.shettydev.chatclient.ui.dynamicColorScheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme(dynamicColorScheme()) {
        Scaffold(
            topBar = { AppTopBar() },
        ) { padding ->
            MainPage(padding)
        }
    }
}