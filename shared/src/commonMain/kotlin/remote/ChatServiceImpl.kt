package remote

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.*
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
    private var socket: DefaultClientWebSocketSession? = null
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

    override fun observeEvents(): Flow<ChatEvent> {
       //TODO: looks nicer but is it actually better?
       return socket?.let { socket ->
            flow {
                while (true) {
                    emit(socket.receiveDeserialized())
                }
            }
        }
        ?: flowOf()

//        return socket
//            ?.incoming
//            ?.receiveAsFlow()
//            ?.mapNotNull {
//                socket?.converter?.deserialize<ChatEvent>(it) //looks ugly
//            }
//            ?: flowOf()
    }

    override suspend fun sendEvent(event: ChatEvent) {
        try {
            socket?.sendSerialized(event)
        } catch (e: Exception) {
            println("Error while sending: " + e.message)
        }
    }

    override suspend fun closeSession() {
        socket?.close()
    }
}