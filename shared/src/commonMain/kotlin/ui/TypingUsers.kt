package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun TypingUsers(typingUsers: Set<String>) {
    val text = if (typingUsers.isEmpty()) {
        ""
    } else if (typingUsers.size == 1) {
        "${typingUsers.single()} is typing"
    } else if (typingUsers.size == 2) {
        val (first, second) = typingUsers.toList()
        "$first and $second are typing"
    } else {
        val list = typingUsers.toList()
        val first = list.take(list.size - 1).joinToString()
        "$first, and ${list.last()} are typing"
    }
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = text,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f),
            style = MaterialTheme.typography.overline
        )
    }
}
