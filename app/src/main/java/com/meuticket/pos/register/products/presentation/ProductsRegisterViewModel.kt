package com.meuticket.pos.register.products.presentation

import com.meuticket.pos.base.BaseViewModel
import com.meuticket.pos.core.livedata.SingleLiveEvent
import com.meuticket.pos.shared.data.model.Product
import com.meuticket.pos.shared.domain.ProductsListInteractor
import javax.inject.Inject

sealed class ProductsRegisterViewModelState {
    class OpenEditScreen(val product: Product): ProductsRegisterViewModelState()
    class ConfirmDelete(val product: Product): ProductsRegisterViewModelState()
}

class ProductsRegisterViewModel @Inject constructor(
    val interactor: ProductsListInteractor
): BaseViewModel() {

    val state = SingleLiveEvent<ProductsRegisterViewModelState>()

    fun editClicked(product: Product) {
        state.value = ProductsRegisterViewModelState.OpenEditScreen(product)
    }

    fun deleteClicked(product: Product) {
        state.value = ProductsRegisterViewModelState.ConfirmDelete(product)
    }

    var products: List<Product> = interactor.listProducts()
}
