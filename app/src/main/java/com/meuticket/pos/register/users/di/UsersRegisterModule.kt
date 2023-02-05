package com.meuticket.pos.register.users.di

import androidx.lifecycle.ViewModel
import com.meuticket.pos.base.ViewModelKey
import com.meuticket.pos.register.users.presentation.UserFormViewModel
import com.meuticket.pos.register.users.presentation.UsersRegisterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class UsersRegisterModule {

    @Binds
    @IntoMap
    @ViewModelKey(UsersRegisterViewModel::class)
    abstract fun bindUsersRegisterViewModel(viewModel: UsersRegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserFormViewModel::class)
    abstract fun bindUserFormViewModel(viewModel: UserFormViewModel): ViewModel
}