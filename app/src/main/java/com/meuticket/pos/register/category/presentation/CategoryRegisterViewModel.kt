package com.meuticket.pos.register.category.presentation

import com.meuticket.pos.base.BaseViewModel
import com.meuticket.pos.core.livedata.SingleLiveEvent
import com.meuticket.pos.shared.data.model.Category
import com.meuticket.pos.shared.domain.CategoryListInteractor
import javax.inject.Inject

sealed class CategoryRegisterViewModelState {
    class OpenEditScreen(val category: Category): CategoryRegisterViewModelState()
    class ConfirmDelete(val category: Category): CategoryRegisterViewModelState()
}

class CategoryRegisterViewModel @Inject constructor(
    val interactor: CategoryListInteractor
): BaseViewModel() {

    val state = SingleLiveEvent<CategoryRegisterViewModelState>()

    fun editClicked(category: Category) {
        state.value = CategoryRegisterViewModelState.OpenEditScreen(category)
    }

    fun deleteClicked(category: Category) {
        state.value = CategoryRegisterViewModelState.ConfirmDelete(category)
    }

    var category: List<Category> = interactor.listCategories()
}
