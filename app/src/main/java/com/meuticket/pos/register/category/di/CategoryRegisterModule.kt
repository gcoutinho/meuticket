package com.meuticket.pos.register.category.di

import androidx.lifecycle.ViewModel
import com.meuticket.pos.base.ViewModelKey
import com.meuticket.pos.register.category.presentation.CategoryFormViewModel
import com.meuticket.pos.register.category.presentation.CategoryRegisterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CategoryRegisterModule {

    @Binds
    @IntoMap
    @ViewModelKey(CategoryRegisterViewModel::class)
    abstract fun bindCategoryRegisterViewModel(viewModel: CategoryRegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoryFormViewModel::class)
    abstract fun bindCategoryFormViewModel(viewModel: CategoryFormViewModel): ViewModel
}