import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import ui.Navigation
import viewmodel.ChatViewModel


@Composable
internal fun App() {
    MaterialTheme {
        val chatViewModel = ChatViewModel()
        Navigation(chatViewModel)
    }
}
