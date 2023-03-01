package com.meuticket.pos

import android.content.Context
import androidx.multidex.MultiDex
import androidx.work.Configuration
import androidx.work.WorkManager
import com.meuticket.pos.core.background.AsyncWorker
import com.meuticket.pos.core.background.WorkerInjectorFactory
import com.meuticket.pos.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class CustomApplication @Inject constructor() : DaggerApplication() {

    @Inject
    lateinit var asyncWorker: AsyncWorker

    @Inject
    lateinit var workerInjectorFactory: WorkerInjectorFactory


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()

        appComponent.inject(this)

        return appComponent
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        initWorkers()
    }

    private fun initWorkers() {

        val config = Configuration.Builder()
            .setWorkerFactory(workerInjectorFactory)
            .build()
        WorkManager.initialize(this, config)

        asyncWorker.downloadProducts(this)
        asyncWorker.downloadUsers(this)
        asyncWorker.downloadCategories(this)
        asyncWorker.downloadEvents(this)
    }
}
