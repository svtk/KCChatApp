package ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import viewmodel.ChatViewModel

@Composable
internal fun Navigation(chatViewModel: ChatViewModel) {
    var loggedIn by rememberSaveable { mutableStateOf(false) }
    var username by rememberSaveable { mutableStateOf("") }
    MaterialTheme {
        if (loggedIn) {
            LaunchedEffect(true) {
                chatViewModel.connectToChat(username)
            }
            ChatScreen(chatViewModel)
        } else {
            WelcomeScreen(onJoinClick = { name ->
                loggedIn = true
                username = name
            })
        }
    }
}
