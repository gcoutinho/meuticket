package com.meuticket.pos.core.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.meuticket.pos.core.storage.LocalStorage
import com.meuticket.pos.shared.data.EventRepository
import com.meuticket.pos.shared.data.model.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider

class EventWorker constructor(
    context: Context,
    params: WorkerParameters,
    private val repository: EventRepository,
    private val localStorage: LocalStorage
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            val data = repository.listFromRemote()
            saveData(data)
            Result.success()
        }
    }

    private fun saveData(data: List<Event>) {
        localStorage.saveEvents(data)
    }

    class Factory @Inject constructor(
        private val repository: Provider<EventRepository>, // <-- Add your providers parameters
        private val localStorage: Provider<LocalStorage> // <-- Add your providers parameters
    ) : IWorkerFactory<EventWorker> {
        override fun create(appContext: Context, params: WorkerParameters): EventWorker {
            return EventWorker(appContext, params, repository.get(), localStorage.get())
        }
    }
}