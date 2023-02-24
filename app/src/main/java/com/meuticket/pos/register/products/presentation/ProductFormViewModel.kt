package com.meuticket.pos.register.products.presentation

import com.meuticket.pos.base.BaseViewModel
import com.meuticket.pos.core.livedata.SingleLiveEvent
import com.meuticket.pos.shared.data.CategoryRepository
import com.meuticket.pos.shared.data.model.Category
import com.meuticket.pos.shared.data.model.Product
import com.meuticket.pos.shared.domain.ProductsListInteractor
import com.meuticket.pos.utils.parseToBigDecimal
import java.util.Locale
import javax.inject.Inject

sealed class ProductFormViewModelState {
    class ShowCategoriesSelector(val categories: List<Category>): ProductFormViewModelState()
    class Error(val message: String): ProductFormViewModelState()
    object SavedSuccess: ProductFormViewModelState()
}

class ProductFormViewModel @Inject constructor(
    val productsListInteractor: ProductsListInteractor
): BaseViewModel() {

    val state = SingleLiveEvent<ProductFormViewModelState>()
    fun categoryClicked() {
        runAsync(
            {
                productsListInteractor.listCategories()
            }, onSuccess = {
                state.value = ProductFormViewModelState.ShowCategoriesSelector(it)
            }
        )
    }

    fun categorySelected(category: Category) {

    }

    fun save(
        product: Product?,
        name: String,
        value: String,
        category: String,
        printReceipt: Boolean
    ) {
        runAsync({
            if(product == null && productsListInteractor.listProducts().firstOrNull { it.name == name } != null) {
                throw java.lang.RuntimeException("Produto já cadastrado")
            }
            productsListInteractor.insertOrUpdate(product, name, value.parseToBigDecimal()?.toDouble()?:0.0, category, printReceipt)
        }, onSuccess = {
            state.value = ProductFormViewModelState.SavedSuccess
        }, onError = {
            state.value = ProductFormViewModelState.Error(it.message?:it.toString())
        })

    }
}