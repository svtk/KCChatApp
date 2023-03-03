package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ui.model.Message
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import ui.model.timeText
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
    Surface {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(Modifier.weight(1f)) {
                    MessageList(
                        messages = messages,
                        username = username,
                    )
                }
                Column {
                    TypingUsers(
                        typingUsers = typingUsers,
                    )
                    CreateMessage(
                        onMessageSent = onMessageSent,
                        onUserIsTyping = onUserIsTyping,
                    )
                }
            }
        }
    }
}
