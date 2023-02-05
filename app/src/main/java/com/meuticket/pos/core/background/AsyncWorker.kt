package com.meuticket.pos.core.background

import android.app.Application
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import javax.inject.Inject

interface AsyncWorker {

    fun downloadOrders()
    fun uploadOrders()

    fun downloadProducts(application: Application)
    fun uploadProducts()
    fun downloadUsers(application: Application)
}

class AsyncWorkerImpl @Inject constructor(): AsyncWorker {
    override fun downloadOrders() {
        TODO("Not yet implemented")
    }

    override fun uploadOrders() {
        TODO("Not yet implemented")
    }

    override fun downloadProducts(application: Application) {

        WorkManager.getInstance(application)
            .enqueue(OneTimeWorkRequest.from(ProductWorker::class.java))
    }

    override fun downloadUsers(application: Application) {
        WorkManager.getInstance(application)
            .enqueue(OneTimeWorkRequest.from(UsersWorker::class.java))
    }

    override fun uploadProducts() {
        TODO("Not yet implemented")
    }

}

