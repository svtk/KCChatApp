package ui.model

import androidx.compose.runtime.Immutable
import kotlinx.datetime.*
import kotlinx.serialization.Serializable
import model.MessageEvent
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours

@Immutable
@Serializable
data class Message(
    val username: String,
    val text: String,
    val timeText: String
)

fun MessageEvent.toMessage() = Message(
    username = username,
    text = messageText,
    timeText = timeText(timestamp)
)

private fun timeText(instant: Instant): String {
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val time = localDateTime.run { LocalTime(hour, minute) }

    val elapsed = Clock.System.now() - instant
    return elapsed.toComponents { _, hours, minutes, _, _ ->
        when {
            elapsed < 1.hours -> "${minutes}m ago"
            elapsed < 1.days -> "${hours}h ago"
            else -> {
                val month = localDateTime.month.name.lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                    .substring(0..2)
                "$month ${localDateTime.dayOfMonth}, $time"
            }
        }
    }
}