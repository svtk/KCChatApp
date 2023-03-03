package remote

import model.ChatEvent
import kotlinx.coroutines.flow.*

interface ChatService {
    suspend fun openSession(username: String)

    fun observeEvents(): Flow<ChatEvent>

    suspend fun sendEvent(event: ChatEvent)

    suspend fun closeSession()

    companion object {
        const val CHAT_PORT = 9010
        const val CHAT_WS_PATH = "/chat"
    }
}

expect val CHAT_HOST: String