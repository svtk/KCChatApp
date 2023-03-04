package util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration

fun tickFlow(tickInterval: Duration): Flow<Unit> = flow {
    while(true) {
        delay(tickInterval)
        emit(Unit)
    }
}