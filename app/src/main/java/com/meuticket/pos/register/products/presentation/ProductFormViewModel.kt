package com.meuticket.pos.register.products.presentation

import com.meuticket.pos.base.BaseViewModel
import com.meuticket.pos.core.livedata.SingleLiveEvent
import com.meuticket.pos.shared.data.CategoryRepository
import com.meuticket.pos.shared.data.model.Category
import javax.inject.Inject

sealed class ProductFormViewModelState {
    class ShowCategoriesSelector(val categories: List<Category>): ProductFormViewModelState()
}

class ProductFormViewModel @Inject constructor(
    val repository: CategoryRepository
): BaseViewModel() {

    val state = SingleLiveEvent<ProductFormViewModelState>()
    fun categoryClicked() {
        state.value = ProductFormViewModelState.ShowCategoriesSelector(repository.listFromLocal())
    }
}