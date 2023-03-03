import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Chat",
        state = rememberWindowState(width = 320.dp, height = 600.dp),
    ) {
        MainView()
    }
}