
package com.meuticket.pos.base

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel : ViewModel(), LifecycleObserver {

    inline fun <R, T> T.runAsync(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        noinline block: suspend T.() -> R,
        crossinline onSuccess: (R) -> Unit = {},
        crossinline onError: (ex: Throwable) -> Unit = {},
    ) {
        viewModelScope.launch {
            withContext(dispatcher) {
                try {
                    val result = block()
                    withContext(Dispatchers.Main) {
                        onSuccess.invoke(result)
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        onError.invoke(e)
                    }
                }
            }
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate() {
        // To be overridden when necessary
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
        // To be overridden when necessary
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
        // To be overridden when necessary
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
        // To be overridden when necessary
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
        // To be overridden when necessary
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        // To be overridden when necessary
    }

    override fun onCleared() {
        super.onCleared()
    }
}
