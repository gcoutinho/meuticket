package com.meuticket.pos.core.session

import com.meuticket.pos.shared.data.model.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Cart @Inject constructor(

) {
    var products: MutableList<Product> = mutableListOf()
}