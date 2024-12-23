package com.shettydev.chatclient

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.shettydev.chatclient.ui.AppTopBar
import com.shettydev.chatclient.ui.MainPage
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold(
            topBar = { AppTopBar() },
        ) { MainPage() }
    }
}