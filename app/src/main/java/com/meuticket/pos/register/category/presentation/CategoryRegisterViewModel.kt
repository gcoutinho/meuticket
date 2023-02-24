package com.meuticket.pos.register.category.presentation

import com.meuticket.pos.base.BaseViewModel
import com.meuticket.pos.core.livedata.SingleLiveEvent
import com.meuticket.pos.core.session.Cart
import com.meuticket.pos.shared.data.model.Category
import com.meuticket.pos.shared.domain.CategoryListInteractor
import javax.inject.Inject

sealed class CategoryRegisterViewModelState {
    class OpenEditScreen(val category: Category): CategoryRegisterViewModelState()
    class ConfirmDelete(val category: Category, val action: () -> Unit): CategoryRegisterViewModelState()
    object ShowErrorCategoryInCart: CategoryRegisterViewModelState()
    object CategoriesLoaded: CategoryRegisterViewModelState()
}

class CategoryRegisterViewModel @Inject constructor(
    val cart: Cart,
    val interactor: CategoryListInteractor
): BaseViewModel() {

    val state = SingleLiveEvent<CategoryRegisterViewModelState>()

    lateinit var categories: List<Category>
    override fun onCreate() {
        super.onCreate()

        loadItems()
    }

    fun loadItems() {
        runAsync({
            interactor.listCategories()
        }, onSuccess = {
            categories = it
            state.value = CategoryRegisterViewModelState.CategoriesLoaded
        })
    }

    fun editClicked(category: Category) {
        if(cart.products.firstOrNull { it.category_uid == category.uid } != null) {
            state.value = CategoryRegisterViewModelState.ShowErrorCategoryInCart
            return
        }
        state.value = CategoryRegisterViewModelState.OpenEditScreen(category)
    }

    fun deleteClicked(category: Category) {

        if(cart.products.firstOrNull { it.category_uid == category.uid } != null) {
            state.value = CategoryRegisterViewModelState.ShowErrorCategoryInCart
            return
        }

        state.value = CategoryRegisterViewModelState.ConfirmDelete(category) {
            runAsync({
                interactor.delete(category)
            }, onSuccess = {
                loadItems()
            })
        }
    }
}
