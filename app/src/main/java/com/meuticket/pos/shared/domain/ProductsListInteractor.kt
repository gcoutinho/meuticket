package com.meuticket.pos.shared.domain

import com.meuticket.pos.shared.data.CategoryRepository
import com.meuticket.pos.shared.data.ProductsRepository
import com.meuticket.pos.shared.data.model.Product
import javax.inject.Inject

interface ProductsListInteractor {

    suspend fun listProducts(): List<Product>

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
}