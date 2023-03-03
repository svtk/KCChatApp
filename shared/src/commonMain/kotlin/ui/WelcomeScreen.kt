package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun WelcomeScreen(onJoinClick: (String) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        var username by rememberSaveable { mutableStateOf("") }
        Column {
            OutlinedTextField(
                modifier = Modifier.width(200.dp),
                value = username,
                onValueChange = { username = it },
                label = { Text(text = "Username") },
            )
            Spacer(modifier = Modifier.size(10.dp))
            Button(
                modifier = Modifier.width(200.dp),
                onClick = { onJoinClick(username) },
                enabled = username.isNotBlank(),
            ) {
                Text(text = "Join the chat")
            }
        }
    }
}