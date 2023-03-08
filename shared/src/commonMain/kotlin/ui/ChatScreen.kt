package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
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

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun ChatScreen(
    messages: ImmutableList<Message>,
    username: String?,
    typingUsers: Set<String>,
    onMessageSent: (String) -> Unit,
    onUserIsTyping: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(Modifier.weight(1f)) {
            ChatSurface {
                Image(
                    painter = painterResource("bg.png"),
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth(),
                )
                MessageList(
                    messages = messages,
                    username = username,
                )
            }
        }
        Column {
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

