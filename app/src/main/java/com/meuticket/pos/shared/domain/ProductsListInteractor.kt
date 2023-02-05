package com.meuticket.pos.shared.domain

import com.meuticket.pos.shared.data.ProductsRepository
import com.meuticket.pos.shared.data.model.Product
import javax.inject.Inject

interface ProductsListInteractor {

    fun listProducts(): List<Product>

}

class ProductsListInteractorImpl @Inject constructor(
    val repository: ProductsRepository
) : ProductsListInteractor {

    override fun listProducts(): List<Product> {
        return repository.listProductsFromLocal()
    }
}