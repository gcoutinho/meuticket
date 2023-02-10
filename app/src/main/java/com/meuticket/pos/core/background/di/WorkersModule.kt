package com.meuticket.pos.core.background.di

import androidx.work.ListenableWorker
import com.meuticket.pos.core.background.*
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
    @WorkerKey(CategoryWorker::class)
    fun bindWorker(worker: CategoryWorker.Factory): IWorkerFactory<out ListenableWorker>

    @Binds
    @IntoMap
    @WorkerKey(UsersWorker::class)
    fun bindUsersWorker(worker: UsersWorker.Factory): IWorkerFactory<out ListenableWorker>

    @Binds
    abstract fun bindAsyncWorker(asyncWorker: AsyncWorkerImpl): AsyncWorker
}