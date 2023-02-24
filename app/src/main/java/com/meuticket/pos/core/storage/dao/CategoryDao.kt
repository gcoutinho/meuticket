package com.meuticket.pos.core.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.meuticket.pos.shared.data.model.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    fun getAll(): List<Category>

    @Query("SELECT * FROM category WHERE uid IN (:categoryIds)")
    fun loadAllByIds(categoryIds: IntArray): List<Category>

    @Query("SELECT * FROM category WHERE name LIKE :first")
    fun findByName(first: String): List<Category>

    @Insert(onConflict = REPLACE)
    fun insertAll(categorys: List<Category>)

    @Delete
    fun delete(category: Category)
    @Insert(onConflict = REPLACE)
    fun save(category: Category)
}