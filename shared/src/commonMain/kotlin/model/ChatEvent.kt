package model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
sealed interface ChatEvent {
    val username: String
    val timestamp: Instant
}

@Serializable
data class MessageEvent(
    override val username: String,
    val messageText: String,
    override val timestamp: Instant,
): ChatEvent

@Serializable
data class TypingEvent(
    override val username: String,
    override val timestamp: Instant,
): ChatEvent