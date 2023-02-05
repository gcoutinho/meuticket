package com.meuticket.pos.shared.data.model

data class Product(
    val id: Int,
    val name: String,
    val image: String,
    val value: Double
) {
    var qtd: Int = 0
}
