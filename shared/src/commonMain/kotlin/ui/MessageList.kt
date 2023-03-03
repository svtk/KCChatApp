package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import ui.model.Message
import ui.model.timeText

@Composable
internal fun MessageList(
    messages: ImmutableList<Message>,
    username: String?,
) {
//    println("Rendering message list for $username, last message: ${messages.firstOrNull()?.text}")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
            .padding(10.dp),
    ) {
        LazyColumn(
            reverseLayout = true,
        ) {
            items(
                items = messages
            ) { event ->
                MessageCard(event, username)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun MessageCard(
    message: Message,
    username: String?
) {
    val isOwnMessage = message.username == username
    Box(
        contentAlignment = if (isOwnMessage) {
            Alignment.CenterEnd
        } else Alignment.CenterStart,
        modifier = Modifier
            .run {
                if (isOwnMessage) {
                    padding(start = 60.dp, end = 20.dp)
                } else {
                    padding(end = 60.dp)
                }
            }
            .fillMaxWidth()
    ) {
        Card {
            Column(
                modifier = Modifier
                    .background(
                        color = if (isOwnMessage)
                            MaterialTheme.colors.secondary.copy(alpha = 0.1f)
                        else
                            MaterialTheme.colors.onPrimary
                    )
            ) {
                Text(
                    text = message.username,
                    style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 5.dp, start = 5.dp, end = 15.dp),
                )
                Text(
                    text = message.text,
                    modifier = Modifier.padding(top = 2.dp, start = 5.dp, end = 15.dp)
                )
                Text(
                    text = message.timeText(),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(start = 20.dp),
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.overline,
                )
            }
        }
    }
}
