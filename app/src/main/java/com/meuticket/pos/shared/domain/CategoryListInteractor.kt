package com.meuticket.pos.shared.domain

import com.meuticket.pos.shared.data.CategoryRepository
import com.meuticket.pos.shared.data.model.Category
import javax.inject.Inject

interface CategoryListInteractor {
    suspend fun listCategories(): List<Category>
}

class CategoryListInteractorImpl @Inject constructor(
    val repository: CategoryRepository
): CategoryListInteractor {

    override suspend fun listCategories(): List<Category> {
        return repository.listFromLocal()
    }

}
