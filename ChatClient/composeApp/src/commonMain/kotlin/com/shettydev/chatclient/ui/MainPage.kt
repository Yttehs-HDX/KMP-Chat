package com.shettydev.chatclient.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shettydev.chatclient.di.koin
import com.shettydev.chatclient.extend.isWsUrl
import com.shettydev.chatclient.viewmodel.ChatMsgUiState
import com.shettydev.chatclient.viewmodel.ChatViewModel
import io.ktor.http.*

@Composable
fun MainPage(padding: PaddingValues) {
    val chatViewModel = koin.get<ChatViewModel>()
    val messages by chatViewModel.messages.collectAsState()

    Column(Modifier.padding(padding)) {
        UrlInputBox(
            onClick = { url ->
                chatViewModel.connect(Url(url))
            }
        )
        MsgColumn(
            modifier = Modifier.weight(1f),
            messages = messages,
        )
        MsgInputBox(
            onClick = { msg ->
                chatViewModel.apply {
                    sendMessage(msg)
                }
            }
        )
    }
}

@Composable
private fun UrlInputBox(onClick: (String) -> Unit) {
    Box(
        modifier = Modifier.padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        val fontStyle = MaterialTheme.typography.bodyMedium
        var textFieldValue by remember { mutableStateOf("ws://localhost:8080/chat") }
        TextField(
            modifier = Modifier.fillMaxWidth(),
            shape = CircleShape,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onSurface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            value = textFieldValue,
            textStyle = fontStyle,
            maxLines = 1,
            onValueChange = { newText ->
                textFieldValue = newText
            },
            placeholder = {
                Text(
                    text = "URL",
                    style = fontStyle,
                )
            },
            trailingIcon = {
                IconButton(
                    modifier = Modifier.padding(8.dp),
                    onClick = {
                        if (textFieldValue.isWsUrl()) {
                            onClick(textFieldValue)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Connect",
                    )
                }
            },
        )
    }
}

@Composable
private fun MsgColumn(
    messages: List<ChatMsgUiState>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier.padding(16.dp)) {
        items(messages.size) { index ->
            val msgUiState = messages[index]
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Card(
                    shape = CircleShape
                ) {
                    Box(
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(msgUiState.username)
                    }
                }
                Card(
                    modifier = Modifier.padding(8.dp),
                ) {
                    Box(
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(msgUiState.message)
                    }
                }
            }
        }
    }
}

@Composable
private fun MsgInputBox(
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
                textColor = MaterialTheme.colorScheme.onSurface,
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