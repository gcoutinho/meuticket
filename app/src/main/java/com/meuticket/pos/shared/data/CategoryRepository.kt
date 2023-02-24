package com.meuticket.pos.shared.data

import com.meuticket.pos.core.storage.LocalStorage
import com.meuticket.pos.shared.data.model.Category
import javax.inject.Inject

interface CategoryRepository {
    suspend fun listFromRemote(): List<Category>
    suspend fun listFromLocal(): List<Category>
}

class CategoryRepositoryImpl @Inject constructor(
    val localStorage: LocalStorage,
    ): CategoryRepository {

    override suspend fun listFromRemote(): List<Category> {
        return mutableListOf(
            Category(
                1,
                "bebidas"
            ),
            Category(
                2,
                "lanches"
            ),
        )
    }

    override suspend fun listFromLocal(): List<Category> {
        return localStorage.getCategories()
    }
}