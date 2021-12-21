package com.eimy.flipboard.core.utils.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

fun <T> asFlow(transform: suspend () -> T): Flow<T> = flow {
    emit(transform())
}

fun <T> loadingEventFlow(transform: suspend () -> Flow<T>): Flow<Event<T>> =
    flow {
        try {
            emit(Event.Loading)
            transform().collect { emit(Event.Data(it)) }
        } catch (error: Throwable) {
            emit(Event.error<T>(error))
        }
    }

fun <T> composeEventFlow(transform: suspend () -> Flow<T>): Flow<Event<T>> =
    flow {
        try {
            transform().collect { emit(Event.Data(it)) }
        } catch (error: Throwable) {
            emit(Event.error<T>(error))
        }
    }

fun <T> eventFlow(transform: suspend () -> T): Flow<Event<T>> =
    flow {
        try {
            emit(Event.Data(transform()))
        } catch (error: Throwable) {
            emit(Event.error<T>(error))
        }
    }