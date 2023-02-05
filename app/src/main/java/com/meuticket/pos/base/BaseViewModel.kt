
package com.meuticket.pos.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

open class BaseViewModel : ViewModel(), LifecycleObserver {

    suspend inline fun <R, T> T.runOn(
        dispatcher: CoroutineDispatcher,
        crossinline block: suspend T.() -> R
    ): Result<R> =
        withContext(dispatcher) {
            try {
                Result.success(block())
            } catch (e: Throwable) {
                Result.failure(e)
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
