package com.meuticket.pos.cart.presentation

import com.meuticket.pos.base.BaseViewModel
import com.meuticket.pos.cart.domain.CartInteractor
import com.meuticket.pos.core.livedata.SingleLiveEvent
import com.meuticket.pos.core.session.Cart
import com.meuticket.pos.shared.data.model.Product
import javax.inject.Inject

sealed class CartViewModelState(){
    class CartLoaded(val cart: Cart): CartViewModelState()
    class ShowQuantityPicker(val product: Product): CartViewModelState()
    class TempCart(val quantity: Int, val value: Double): CartViewModelState()
    class ShowDeleteDialog(val product: Product, val action: () -> Unit) : CartViewModelState()

    object HideQuantityPicker: CartViewModelState()
}

class CartViewModel @Inject constructor(
    val interactor: CartInteractor
): BaseViewModel() {

    lateinit var cart: Cart

    val state = SingleLiveEvent<CartViewModelState>()

    override fun onResume() {
        super.onResume()

        runAsync(
            {
                interactor.loadCart()
            }, onSuccess = {
                cart = it
                state.value = CartViewModelState.CartLoaded(it)
            }
        )
    }

    fun editClicked(product: Product) {
        state.value = CartViewModelState.ShowQuantityPicker(product)
    }

    fun deleteClicked(product: Product) {
        state.value = CartViewModelState.ShowDeleteDialog(product) {
            delete(product)
        }
    }

    private fun delete(product: Product) {
        cart.products.remove(product)
        state.value = CartViewModelState.CartLoaded(cart)
    }

    fun totalCart() = cart.products.sumOf { it.qtd*it.value }

    fun updateCart(product: Product, qtd: Int) {
        cart.products.find { it.uid == product.uid }?.let { it.qtd = qtd }
        state.value = CartViewModelState.CartLoaded(cart)
    }

    fun addOne(actual: String, product: Product) {
        state.value = CartViewModelState.TempCart(actual.toInt()+1, product.value*(actual.toInt()+1))
    }

    fun removeOne(actual: String, product: Product) {
        if(actual.toInt() > 1)
            state.value = CartViewModelState.TempCart(actual.toInt()-1, product.value*(actual.toInt()-1))
    }
}