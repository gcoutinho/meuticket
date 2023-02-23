package com.meuticket.pos.cart.di

import androidx.lifecycle.ViewModel
import com.meuticket.pos.base.ViewModelKey
import com.meuticket.pos.cart.domain.CartInteractor
import com.meuticket.pos.cart.domain.CartInteractorImpl
import com.meuticket.pos.cart.presentation.CartViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CartModule {

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    abstract fun bindViewModel(viewModel: CartViewModel): ViewModel

    @Binds
    abstract fun bindInteractor(interactor: CartInteractorImpl): CartInteractor

}