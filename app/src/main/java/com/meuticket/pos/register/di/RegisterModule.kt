package com.meuticket.pos.register.di

import androidx.lifecycle.ViewModel
import com.meuticket.pos.base.ViewModelKey
import com.meuticket.pos.register.domain.RegisterInteractor
import com.meuticket.pos.register.presentation.RegisterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class RegisterModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindViewModel(viewModel: RegisterViewModel): ViewModel

    @Binds
    abstract fun bindInteractor(interactor: RegisterInteractor): RegisterInteractor
}