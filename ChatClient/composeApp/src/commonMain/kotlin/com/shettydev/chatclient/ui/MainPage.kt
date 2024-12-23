package com.shettydev.chatclient.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shettydev.chatclient.di.koin
import com.shettydev.chatclient.viewmodel.ChatViewModel

@Composable
fun MainPage(padding: PaddingValues) {
    val chatViewModel = koin.get<ChatViewModel>()
    val messages by chatViewModel.messages.collectAsState()

    Column(Modifier.padding(padding)) {
        MessageColumn(
            modifier = Modifier.weight(1f),
            messages = messages,
        )
        InputBox(
            onClick = { msg ->
                chatViewModel.sendMessage(msg)
            }
        )
    }
}

@Composable
private fun MessageColumn(
    messages: List<String>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier.padding(16.dp)) {
        items(messages.size) { index ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Card(
                    shape = CircleShape
                ) {
                    Box(
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("user")
                    }
                }
                Card(
                    modifier = Modifier.padding(8.dp),
                ) {
                    Box(
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(messages[index])
                    }
                }
            }
        }
    }
}

@Composable
private fun InputBox(
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
    onClick: (String) -> Unit = {},
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .height(56.dp),
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement,
    ) {
        val fontStyle = MaterialTheme.typography.bodyLarge

        var textFieldValue by remember { mutableStateOf("") }
        TextField(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            shape = CircleShape,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            value = textFieldValue,
            textStyle = fontStyle,
            maxLines = 2,
            onValueChange = { newText ->
                textFieldValue = newText
            },
            placeholder = {
                Text(
                    text = "Typing ...",
                    style = fontStyle,
                )
            },
        )

        val btnContainerColor = MaterialTheme.colorScheme.primaryContainer

        Button(
            modifier = Modifier.fillMaxHeight(),
            colors = ButtonDefaults.buttonColors(
                containerColor = btnContainerColor,
                contentColor = MaterialTheme.colorScheme.contentColorFor(btnContainerColor),
            ),
            onClick = {
                if (textFieldValue.isNotEmpty()) {
                    onClick(textFieldValue)
                    textFieldValue = ""
                }
            },
        ) {
            Text(
                text = "Send",
                style = fontStyle,
            )
        }
    }
}