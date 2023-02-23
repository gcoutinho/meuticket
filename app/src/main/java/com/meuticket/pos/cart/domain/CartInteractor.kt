package com.meuticket.pos.cart.domain

import com.meuticket.pos.core.session.Cart
import javax.inject.Inject

interface CartInteractor {
    fun loadCart(): Cart

}
class CartInteractorImpl @Inject constructor(
    val cart: Cart
): CartInteractor {
    override fun loadCart(): Cart {
        return cart
    }
}