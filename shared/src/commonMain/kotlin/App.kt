import androidx.compose.runtime.Composable
import ui.Navigation
import ui.theme.ChatAppTheme
import viewmodel.ChatViewModel


@Composable
internal fun App() {
    ChatAppTheme {
        val chatViewModel = ChatViewModel()
        Navigation(chatViewModel)
    }
}
