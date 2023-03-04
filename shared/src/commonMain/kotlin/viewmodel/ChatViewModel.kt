package viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.collections.immutable.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import model.MessageEvent
import model.TypingEvent
import remote.ChatService
import remote.ChatServiceImpl
import ui.model.Message
import ui.model.toMessage
import util.Ticker
import util.tickFlow
import kotlin.time.Duration.Companion.seconds

class ChatViewModel: CommonViewModel() {
    private val chatService: ChatService = ChatServiceImpl()
    private var _username = mutableStateOf<String?>(null)
    val username: State<String?> = _username

    private val _messageEvents: MutableStateFlow<List<MessageEvent>> = MutableStateFlow(listOf())
    private val _messagesFlow: MutableStateFlow<List<Message>> = MutableStateFlow(listOf())
    val messagesFlow: Flow<ImmutableList<Message>>
        get() = _messagesFlow.map { it.toImmutableList() }

    private val _typingEvents: MutableStateFlow<List<TypingEvent>> = MutableStateFlow(listOf())
    val typingUsers: Flow<ImmutableSet<String>>
        get() = _typingEvents
            .map { it.map(TypingEvent::username).toImmutableSet() }

    private val ticker = Ticker(viewModelScope, 1.seconds)

    fun connectToChat(username: String) {
        _username.value = username
        viewModelScope.launch {
            chatService.openSession(username)
            chatService.observeEvents()
                .onEach { event ->
                    when (event) {
                        is MessageEvent -> {
                            _messageEvents.update { list -> list + event }
                            _messagesFlow.update {
                                _messageEvents.value.map { it.toMessage() }
                            }
                            _typingEvents.update { events ->
                                // cancelling typing events from a user if this user has written a message
                                events.filter { it.username != event.username }
                            }
                        }

                        is TypingEvent -> {
                            if (event.username != username) {
                                _typingEvents.update { it + event }
                            }
                            viewModelScope.launch {
                                delay(3000)
                                _typingEvents.update { it - event }
                            }
                        }
                    }
                }
                .launchIn(viewModelScope)
            tickFlow(1.seconds).collect {
                _messagesFlow.update {
                    _messageEvents.value.map { it.toMessage() }
                }
            }
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            chatService.closeSession()
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            username.value?.let { name ->
                chatService.sendEvent(
                    MessageEvent(username = name, messageText = message, timestamp = Clock.System.now())
                )
            }
        }
    }

    fun startTyping() {
        viewModelScope.launch {
            username.value?.let { name ->
                chatService.sendEvent(
                    TypingEvent(username = name, timestamp = Clock.System.now())
                )
            }
        }
    }
}