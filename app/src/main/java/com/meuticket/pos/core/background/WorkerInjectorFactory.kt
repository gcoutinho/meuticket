package com.meuticket.pos.core.background

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

class WorkerInjectorFactory @Inject constructor(
    private val workerFactoryMap: Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<IWorkerFactory<out ListenableWorker>>>
) : WorkerFactory() {

    override fun createWorker(appContext: Context, workerClassName: String, workerParameters: WorkerParameters)
            : ListenableWorker? {

        val entry = workerFactoryMap.entries.find { Class.forName(workerClassName).isAssignableFrom(it.key) }

        val factory = entry?.value ?: throw IllegalArgumentException("could not find worker: $workerClassName")

        return factory.get().create(appContext, workerParameters)
    }
}