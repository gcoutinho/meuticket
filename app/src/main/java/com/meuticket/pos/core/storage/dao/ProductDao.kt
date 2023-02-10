package com.meuticket.pos.core.storage.dao

import androidx.room.*
import com.meuticket.pos.shared.data.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getAll(): List<Product>

    @Query("SELECT * FROM product WHERE uid IN (:productIds)")
    fun loadAllByIds(productIds: IntArray): List<Product>

    @Query("SELECT * FROM product WHERE name LIKE :first")
    fun findByName(first: String): List<Product>

    @Query("SELECT * FROM product " +
            "INNER JOIN category ON category.uid = product.category_uid" +
            " WHERE product.category_uid == :first")
    fun findByCategory(first: Int): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<Product>)

    @Delete
    fun delete(product: Product)
}