package com.shettydev.chatclient.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily

@Composable
fun AppTopBar() {
    Row {
        Spacer(Modifier.weight(1f))
        Text(
            text = "Chat Client",
            style = MaterialTheme.typography.headlineLarge,
            fontFamily = FontFamily.Cursive,
        )
        Spacer(Modifier.weight(1f))
    }
}