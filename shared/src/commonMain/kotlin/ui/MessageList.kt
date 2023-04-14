package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import kotlinx.collections.immutable.toPersistentList
import ui.model.Message
import ui.model.timeText
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun MessageList(
    messages: ImmutableList<Message>,
    username: String?,
) {
//    println("Rendering message list for $username, last message: ${messages.lastOrNull()?.text}")

    val listState = rememberLazyListState()
    if (messages.isNotEmpty()) {
        LaunchedEffect(messages.last()) {
            listState.animateScrollToItem(messages.lastIndex, scrollOffset = 2)
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp).padding(top = 6.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        state = listState
    ) {
        val grouped = messages.groupConsecutiveBy { first, second ->
            first.username == second.username &&
            second.timestamp - first.timestamp < 10.seconds
        }
        grouped.forEach { group ->
            item {
                MessageCard(
                    group.toPersistentList(),
                    isMyMessage = group.first().username == username
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun MessageCard(
    messages: ImmutableList<Message>,
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
        val shape = RoundedCornerShape(16.dp).run {
            val cornerSize = CornerSize(4.dp)
            if (isMyMessage) copy(topEnd = cornerSize) else copy(topStart = cornerSize)
        }
        val color = if (isMyMessage) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
        Card(
            shape = shape,
        ) {
            Column {
                Text(
                    text = messages.first().username,
                    color = color,
                    style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 20.dp),
                )
                for (message in messages) {
                    Text(
                        text = message.text,
                        modifier = Modifier.padding(top = 5.dp, start = 10.dp, end = 15.dp)
                    )
                }
                Text(
                    text = messages.last().timeText(),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 5.dp, start = 20.dp, end = 10.dp, bottom = 3.dp),
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.overline,
                )
            }
        }
    }
}


fun <T> Iterable<T>.groupConsecutiveBy(groupIdentifier: (T, T) -> Boolean) =
    if (!this.any())
        emptyList()
    else this
        .drop(1)
        .fold(mutableListOf(mutableListOf(this.first()))) { groups, t ->
            groups.last().apply {
                if (groupIdentifier(last(), t)) {
                    add(t)
                } else {
                    groups.add(mutableListOf(t))
                }
            }
            groups
        }