package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

    val listState = rememberLazyListState()
    if (messages.isNotEmpty()) {
        LaunchedEffect(messages.last()) {
            listState.animateScrollToItem(messages.lastIndex, scrollOffset = 2)
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        state = listState
    ) {
        items(
            items = messages
        ) { message ->
            MessageCard(message, isMyMessage = message.username == username)
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun MessageCard(
    message: Message,
    isMyMessage: Boolean,
) {
    Box(
        contentAlignment = if (isMyMessage) Alignment.CenterEnd else Alignment.CenterStart,
        modifier = Modifier
            .run {
                if (isMyMessage) padding(start = 60.dp) else padding(end = 60.dp)
            }
            .fillMaxWidth()
    ) {
        val shape = RoundedCornerShape(12.dp).run {
            val cornerSize = CornerSize(2.dp)
            if (isMyMessage) copy(topEnd = cornerSize) else copy(topStart = cornerSize)
        }
        Card(
            shape =  shape,
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = if (isMyMessage)
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
                    modifier = Modifier.padding(top = 3.dp, start = 5.dp, end = 15.dp)
                )
                Text(
                    text = message.timeText(),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(start = 20.dp, end = 6.dp),
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.overline,
                )
            }
        }
    }
}
