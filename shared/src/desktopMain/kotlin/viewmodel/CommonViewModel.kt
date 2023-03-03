package viewmodel

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

actual abstract class CommonViewModel {

    actual val viewModelScope = MainScope()

    protected actual open fun onCleared() {
    }

    fun clear() {
        onCleared()
        viewModelScope.cancel()
    }
}