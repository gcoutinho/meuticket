package com.meuticket.pos.shared.domain

import com.meuticket.pos.shared.data.CategoryRepository
import com.meuticket.pos.shared.data.model.Category
import javax.inject.Inject

interface CategoryListInteractor {
    fun listCategories(): List<Category>
}

class CategoryListInteractorImpl @Inject constructor(
    val repository: CategoryRepository
): CategoryListInteractor {

    override fun listCategories(): List<Category> {
        return repository.listFromLocal()
    }

}
