package com.meuticket.pos.core.background.di

import androidx.work.ListenableWorker
import com.meuticket.pos.core.background.AsyncWorker
import com.meuticket.pos.core.background.AsyncWorkerImpl
import com.meuticket.pos.core.background.IWorkerFactory
import com.meuticket.pos.core.background.ProductWorker
import com.meuticket.pos.core.background.UsersWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Suppress("unused")
@Module
interface WorkersModule {

    @Binds
    @IntoMap
    @WorkerKey(ProductWorker::class)
    fun bindProductWorker(worker: ProductWorker.Factory): IWorkerFactory<out ListenableWorker>

    @Binds
    @IntoMap
    @WorkerKey(UsersWorker::class)
    fun bindUsersWorker(worker: UsersWorker.Factory): IWorkerFactory<out ListenableWorker>

    @Binds
    abstract fun bindAsyncWorker(asyncWorker: AsyncWorkerImpl): AsyncWorker
}