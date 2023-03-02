package com.meuticket.pos.admin.di

import androidx.lifecycle.ViewModel
import com.meuticket.pos.admin.presentation.AdminViewModel
import com.meuticket.pos.base.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AdminModule {

    @Binds
    @IntoMap
    @ViewModelKey(AdminViewModel::class)
    abstract fun bindViewModel(viewModel: AdminViewModel): ViewModel

}