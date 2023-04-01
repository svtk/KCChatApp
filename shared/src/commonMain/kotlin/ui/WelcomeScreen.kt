package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun WelcomeScreen(onJoinClick: (String) -> Unit) {
    ChatSurface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card {
                var username by rememberSaveable { mutableStateOf("") }
                Column(modifier = Modifier.padding(20.dp)) {
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
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun ChatSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(modifier) {
        Image(
            painter = painterResource("bg.png"),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            content()
        }
    }
}