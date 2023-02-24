package com.meuticket.pos.register.category.presentation

import com.meuticket.pos.base.BaseViewModel
import com.meuticket.pos.core.livedata.SingleLiveEvent
import com.meuticket.pos.shared.data.model.Category
import com.meuticket.pos.shared.domain.CategoryListInteractor
import javax.inject.Inject

sealed class CategoryFormViewModelState {

    class Error(val message: String): CategoryFormViewModelState()
    object SavedSuccess: CategoryFormViewModelState()
}
class CategoryFormViewModel @Inject constructor(
    val interactor: CategoryListInteractor
): BaseViewModel() {

    val state = SingleLiveEvent<CategoryFormViewModelState>()
    fun save(category: Category?, name: String) {
        runAsync({
            if(category == null && interactor.listCategories().firstOrNull { it.name == name } != null)
                throw java.lang.RuntimeException("Categoria já existe")
            if (name.length < 3)
                throw java.lang.RuntimeException("Nome precisa ter pelo menos 3 caracteres")
            interactor.save(category, name)
        }, onSuccess = {
            state.value = CategoryFormViewModelState.SavedSuccess
        }, onError = {
            state.value = CategoryFormViewModelState.Error(it.message?:it.toString())
        })
    }
}