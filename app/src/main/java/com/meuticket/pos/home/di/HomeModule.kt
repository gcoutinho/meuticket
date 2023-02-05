package com.meuticket.pos.home.di

import androidx.lifecycle.ViewModel
import com.meuticket.pos.base.ViewModelKey
import com.meuticket.pos.home.domain.HomeInteractor
import com.meuticket.pos.home.presentation.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    abstract fun bindInteractor(interactor: HomeInteractor): HomeInteractor
}