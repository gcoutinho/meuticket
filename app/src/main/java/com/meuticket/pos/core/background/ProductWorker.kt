package com.meuticket.pos.core.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.meuticket.pos.core.storage.LocalStorage
import com.meuticket.pos.shared.data.ProductsRepository
import com.meuticket.pos.shared.data.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider

class ProductWorker constructor(
    context: Context,
    params: WorkerParameters,
    private val repository: ProductsRepository,
    private val localStorage: LocalStorage
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            val data = repository.listProductsFromRemote()
            saveData(data)
            Result.success()
        }
    }

    private fun saveData(data: List<Product>) {
        localStorage.saveProducts(data)
    }

    class Factory @Inject constructor(
        private val repository: Provider<ProductsRepository>, // <-- Add your providers parameters
        private val localStorage: Provider<LocalStorage> // <-- Add your providers parameters
    ) : IWorkerFactory<ProductWorker> {
        override fun create(appContext: Context, params: WorkerParameters): ProductWorker {
            return ProductWorker(appContext, params, repository.get(), localStorage.get())
        }
    }
}