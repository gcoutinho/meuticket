package com.meuticket.pos.shared.domain

import com.meuticket.pos.shared.data.CategoryRepository
import com.meuticket.pos.shared.data.model.Category
import java.util.UUID
import javax.inject.Inject

interface CategoryListInteractor {
    suspend fun listCategories(): List<Category>
    suspend fun delete(category: Category)
    suspend fun save(category: Category?, name: String)
}

class CategoryListInteractorImpl @Inject constructor(
    val repository: CategoryRepository
): CategoryListInteractor {

    override suspend fun listCategories(): List<Category> {
        return repository.listFromLocal()
    }

    override suspend fun delete(category: Category) {
        repository.delete(category)
    }

    override suspend fun save(category: Category?, name: String) {
        repository.save(Category(category?.uid?:UUID.randomUUID().toString(), name))
    }

}
