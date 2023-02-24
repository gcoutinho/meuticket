package com.meuticket.pos.shared.domain

import com.meuticket.pos.shared.data.CategoryRepository
import com.meuticket.pos.shared.data.ProductsRepository
import com.meuticket.pos.shared.data.model.Category
import com.meuticket.pos.shared.data.model.Product
import javax.inject.Inject

interface ProductsListInteractor {

    suspend fun listProducts(): List<Product>
    suspend fun listCategories(): List<Category>
    suspend fun insertOrUpdate(
        product: Product?,
        name: String,
        value: Double,
        category: String,
        printReceipt: Boolean
    )

    suspend fun delete(product: Product)

}

class ProductsListInteractorImpl @Inject constructor(
    val repository: ProductsRepository,
    val categoryRepository: CategoryRepository
) : ProductsListInteractor {

    override suspend fun listProducts(): List<Product> {
        return repository.listProductsFromLocal().map { product ->
            product.apply {
                category = categoryRepository.listFromLocal().first { product.category_uid == it.uid }
            }
        }
    }

    override suspend fun listCategories(): List<Category> {
        return categoryRepository.listFromLocal()
    }

    override suspend fun insertOrUpdate(
        product: Product?,
        name: String,
        value: Double,
        category: String,
        printReceipt: Boolean
    ) {
        val categoryId = categoryRepository.listFromLocal().first { it.name == category }.uid
        if(product != null) {
            repository.update(Product(product.uid, name, product.image, value, categoryId).apply { this.printReceipt = printReceipt })
        } else {
            repository.insert(Product(name = name, image = "", value = value, category_uid = categoryId).apply { this.printReceipt = printReceipt })
        }
    }

    override suspend fun delete(product: Product) {
        repository.delete(product)
    }
}