package com.eimy.flipboard.base

import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import com.eimy.flipboard.core.utils.flow.Event
import java.net.SocketTimeoutException

open class NoInternetBaseViewModel : ViewModel() {
    val noConnectionEvent: LiveEvent<Pair<Boolean, Throwable?>> = LiveEvent()

    fun updateNoConnectionEvent(status: Boolean, error: Throwable?) {
        noConnectionEvent.postValue(Pair(status, error))
    }

    fun updateNoConnectionEvent(tag: String, status: Boolean, error: Throwable?) {
        noConnectionEvent.postValue(Pair(status, error))
    }

//    fun hasInternetConnection(errorEvent: Event.Error): Boolean =
//        errorEvent.error !is NoInternetException && errorEvent.error !is SocketTimeoutException
}