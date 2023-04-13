package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import ui.model.Message
import viewmodel.ChatViewModel

@Composable
internal fun ChatScreen(chatViewModel: ChatViewModel) {
    ChatScreen(
        messages = chatViewModel.messagesFlow.collectAsState(persistentListOf()).value,
        username = chatViewModel.username.value,
        typingUsers = chatViewModel.typingUsers.collectAsState(persistentSetOf()).value,
        onMessageSent = chatViewModel::sendMessage,
        onUserIsTyping = chatViewModel::startTyping,
    )
}

@Composable
internal fun ChatScreen(
    messages: ImmutableList<Message>,
    username: String?,
    typingUsers: Set<String>,
    onMessageSent: (String) -> Unit,
    onUserIsTyping: () -> Unit,
) {
    ChatSurface {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(Modifier.weight(1f)) {
                MessageList(
                    messages = messages,
                    username = username,
                )
            }
            Column(
                modifier = Modifier.background(MaterialTheme.colors.background)
            ) {
                TypingUsers(
                    typingUsers = typingUsers,
                )
                CreateMessage(
                    onMessageSent = onMessageSent,
                    onTyping = onUserIsTyping,
                )
            }
        }
    }
}
