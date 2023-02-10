package com.meuticket.pos.ticket.presentation

import androidx.lifecycle.viewModelScope
import com.meuticket.pos.base.BaseViewModel
import com.meuticket.pos.shared.data.model.Product
import com.meuticket.pos.shared.domain.ProductsListInteractor
import com.meuticket.pos.core.livedata.SingleLiveEvent
import com.meuticket.pos.core.session.Cart
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProductListViewModelState {
    class ShowQuantityPicker(val product: Product): ProductListViewModelState()
    class HideQuantityPicker(val product: Product): ProductListViewModelState()
    class TempCart(val quantity: Int, val value: Double): ProductListViewModelState()
    class CartUpdated(val value: Double): ProductListViewModelState()
    object ProductsLoaded: ProductListViewModelState()
}


class ProductListViewModel @Inject constructor(
    val interactor: ProductsListInteractor,
    val cart: Cart
): BaseViewModel() {

    var state = SingleLiveEvent<ProductListViewModelState>()

    lateinit var products: List<Product>

    override fun onCreate() {
        super.onCreate()

        runAsync(block = {
            products = interactor.listProducts()
        }, onSuccess = {
            state.value = ProductListViewModelState.ProductsLoaded
        })

        state.value = ProductListViewModelState.CartUpdated(cart.products.sumOf { it.qtd*it.value })
    }

    fun productClicked(product: Product, isSelected: Boolean) {
        state.value = ProductListViewModelState.TempCart(0, 0.0)
        state.value = ProductListViewModelState.HideQuantityPicker(product)

        if(isSelected)
            state.value = ProductListViewModelState.ShowQuantityPicker(product)
    }

    fun removeOne(product: Product) {
        state.value.let {
            if(it is ProductListViewModelState.TempCart) {
                if(it.quantity > 0) {
                    state.value = ProductListViewModelState.TempCart(it.quantity-1, value = product.value*it.quantity-1)
                }
            }
        }
    }

    fun addOne(product: Product) {
        state.value.let {
            if(it is ProductListViewModelState.TempCart) {
                if(it.quantity < 10) {
                    state.value = ProductListViewModelState.TempCart(it.quantity+1, value = product.value*it.quantity+1)
                }
            } else {
                state.value = ProductListViewModelState.TempCart(1, value = product.value)
            }
        }
    }

    fun addToCart(product: Product, qtd: Int) {
        if(cart.products.contains(product)) {
            cart.products.find { it == product }?.let {
                it.qtd += qtd
            }
        } else {
            cart.products.add(product.apply { this.qtd = qtd })
        }
        state.value = ProductListViewModelState.CartUpdated(cart.products.sumOf { it.qtd*it.value })
        state.value = ProductListViewModelState.TempCart(0, 0.0)
        state.value = ProductListViewModelState.HideQuantityPicker(product)
    }

}
