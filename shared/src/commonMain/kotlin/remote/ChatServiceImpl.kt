package remote

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import model.ChatEvent
import remote.ChatService.Companion.CHAT_PORT
import remote.ChatService.Companion.CHAT_WS_PATH

class ChatServiceImpl : ChatService {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        install(WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(Json)
        }
    }
    private lateinit var socket: DefaultClientWebSocketSession
    private lateinit var username: String

    override suspend fun openSession(username: String) {
        try {
            this.username = username
            socket = client.webSocketSession(
                method = HttpMethod.Get,
                host = CHAT_HOST,
                port = CHAT_PORT,
                path = CHAT_WS_PATH
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun observeEvents(): Flow<ChatEvent> =
        flow {
            while (true) {
                emit(socket.receiveDeserialized())
            }
        }

    override suspend fun sendEvent(event: ChatEvent) {
        try {
            socket.sendSerialized(event)
        } catch (e: Exception) {
            println("Error while sending: " + e.message)
        }
    }

    override suspend fun closeSession() {
        socket.close()
    }
}