package com.meuticket.pos.shared.data.model

data class Product(
    val id: Int,
    val name: String,
    val image: String,
    val value: Double,
    val category: Category
): java.io.Serializable {
    var qtd: Int = 0
    var secondWay: Boolean = false
}
