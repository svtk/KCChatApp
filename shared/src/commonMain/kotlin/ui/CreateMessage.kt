package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun CreateMessage(
    onMessageSent: (String) -> Unit,
    onUserIsTyping: () -> Unit
) {
    var message by remember { mutableStateOf("") }
    Card(Modifier.padding(10.dp)) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.9f),
                value = message,
                onValueChange = {
                    message = it
                    onUserIsTyping()
                },
                label = { Text("Message") },
            )
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                val enabled = message.isNotBlank()
                IconButton(
                    onClick = {
                        onMessageSent(message)
                        message = ""
                    },
                    enabled = enabled,
                    modifier = Modifier.padding(start = 10.dp),
                ) {
                    Icons.Filled.Send
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "Send",
                        modifier = Modifier.size(40.dp),
                        tint = if (enabled)
                            MaterialTheme.colors.primary
                        else
                            MaterialTheme.colors.onBackground.copy(alpha = 0.3f),
                    )
                }
            }
        }
    }
}