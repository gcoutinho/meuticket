package com.meuticket.pos.register.products.presentation

import com.meuticket.pos.base.BaseViewModel
import com.meuticket.pos.core.livedata.SingleLiveEvent
import com.meuticket.pos.core.session.Cart
import com.meuticket.pos.shared.data.model.Product
import com.meuticket.pos.shared.domain.ProductsListInteractor
import javax.inject.Inject

sealed class ProductsRegisterViewModelState {
    class OpenEditScreen(val product: Product): ProductsRegisterViewModelState()
    class ConfirmDelete(val product: Product, val action: () -> Unit): ProductsRegisterViewModelState()
    object ProductsLoaded: ProductsRegisterViewModelState()
    object ProductInCartError: ProductsRegisterViewModelState()
}

class ProductsRegisterViewModel @Inject constructor(
    val cart: Cart,
    val interactor: ProductsListInteractor
): BaseViewModel() {

    val state = SingleLiveEvent<ProductsRegisterViewModelState>()
    lateinit var products: List<Product>

    override fun onCreate() {
        super.onCreate()

        loadItems()
    }

    fun editClicked(product: Product) {
        if(cart.products.contains(product)) {
            state.value = ProductsRegisterViewModelState.ProductInCartError
            return
        }
        state.value = ProductsRegisterViewModelState.OpenEditScreen(product)
    }

    fun deleteClicked(product: Product) {
        if(cart.products.contains(product)) {
            state.value = ProductsRegisterViewModelState.ProductInCartError
            return
        }

        state.value = ProductsRegisterViewModelState.ConfirmDelete(product) {
            runAsync({
                interactor.delete(product)
            }, onSuccess = {
                loadItems()
            })
        }
    }

    fun loadItems() {
        runAsync(
            {
                products = interactor.listProducts()
            }, onSuccess = {
                state.value = ProductsRegisterViewModelState.ProductsLoaded
            }
        )
    }

}
