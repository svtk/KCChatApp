package viewmodel

import kotlinx.coroutines.CoroutineScope

expect abstract class CommonViewModel() {
    val viewModelScope: CoroutineScope
    protected open fun onCleared()
}