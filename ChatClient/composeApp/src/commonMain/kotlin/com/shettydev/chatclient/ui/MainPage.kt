package com.shettydev.chatclient.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shettydev.chatclient.di.koin
import com.shettydev.chatclient.viewmodel.ChatViewModel

@Composable
fun MainPage(padding: PaddingValues) {
    val chatViewModel = koin.get<ChatViewModel>()
    val messages by chatViewModel.messages.collectAsState()

    Column {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(top = padding.calculateTopPadding()),
            userScrollEnabled = true,
        ) {
            items(messages.size) { index ->
                Card {
                    Row {
                        Spacer(Modifier.weight(1f))
                        Text(messages[index])
                        Spacer(Modifier.weight(1f))
                    }
                }
                Spacer(Modifier.width(8.dp))
            }
        }

        Row {
            var textFieldValue by remember { mutableStateOf("") }
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = textFieldValue,
                maxLines = 2,
                onValueChange = { newText ->
                    textFieldValue = newText
                },
            )
            TextButton(
                onClick = {
                    chatViewModel.sendMessage(textFieldValue)
                    textFieldValue = ""
                }
            ) {
                Text("Send Message")
            }
        }
    }
}