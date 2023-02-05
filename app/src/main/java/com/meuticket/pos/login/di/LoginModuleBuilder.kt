package com.meuticket.pos.login.di

import com.meuticket.pos.login.presentation.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginModuleBuilder {

    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun bindLoginActivity(): LoginActivity
}