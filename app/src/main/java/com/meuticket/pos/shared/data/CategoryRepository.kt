package com.meuticket.pos.shared.data

import com.meuticket.pos.core.storage.LocalStorage
import com.meuticket.pos.shared.data.model.Category
import javax.inject.Inject

interface CategoryRepository {
    fun listFromRemote(): List<Category>
    fun listFromLocal(): List<Category>
}

class CategoryRepositoryImpl @Inject constructor(
    val localStorage: LocalStorage,
    ): CategoryRepository {

    override fun listFromRemote(): List<Category> {
        return mutableListOf(
            Category(
                "bebidas"
            ),
            Category(
                "lanches"
            ),
        )
    }

    override fun listFromLocal(): List<Category> {
        return localStorage.getCategories()
    }
}