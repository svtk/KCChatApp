package ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun CreateMessage(
    onMessageSent: (String) -> Unit,
    onTyping: () -> Unit
) {
    var message by remember { mutableStateOf("") }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(bottom = 16.dp),
        value = message,
        onValueChange = {
            message = it
            onTyping()
        },
        label = { Text("Message") },
        trailingIcon = {
            if (message.isNotBlank()) {
                IconButton(
                    onClick = {
                        onMessageSent(message)
                        message = ""
                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "Send",
                        tint = MaterialTheme.colors.primary,
                    )
                }
            }
        }
    )
}