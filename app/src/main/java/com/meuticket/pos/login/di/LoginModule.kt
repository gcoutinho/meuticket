package com.meuticket.pos.login.di

import androidx.lifecycle.ViewModel
import com.meuticket.pos.base.ViewModelKey
import com.meuticket.pos.login.domain.LoginInteractor
import com.meuticket.pos.login.domain.LoginInteractorImpl
import com.meuticket.pos.login.presentation.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LoginModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    abstract fun bindInteractor(interactor: LoginInteractorImpl): LoginInteractor
}