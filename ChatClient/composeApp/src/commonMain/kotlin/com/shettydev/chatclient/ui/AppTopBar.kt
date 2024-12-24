package com.shettydev.chatclient.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppTopBar() {
    Row(Modifier.padding(16.dp)) {
        Text(
            text = "Chat Client",
            style = MaterialTheme.typography.headlineLarge,
        )
    }
}