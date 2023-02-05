package com.meuticket.pos.register.products.di

import androidx.lifecycle.ViewModel
import com.meuticket.pos.base.ViewModelKey
import com.meuticket.pos.register.products.presentation.ProductFormViewModel
import com.meuticket.pos.register.products.presentation.ProductsRegisterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ProductsRegisterModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProductsRegisterViewModel::class)
    abstract fun bindProductsRegisterViewModel(viewModel: ProductsRegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductFormViewModel::class)
    abstract fun bindProductFormViewModel(viewModel: ProductFormViewModel): ViewModel
}