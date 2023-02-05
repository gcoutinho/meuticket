package com.meuticket.pos.shared.di

import com.meuticket.pos.shared.data.UsersRepository
import com.meuticket.pos.shared.data.UsersRepositoryImpl
import com.meuticket.pos.shared.domain.UsersListInteractor
import com.meuticket.pos.shared.domain.UsersListInteractorImpl
import dagger.Binds
import dagger.Module

@Module
abstract class UsersModule {

    @Binds
    abstract fun bindInteractor(interactor: UsersListInteractorImpl): UsersListInteractor

    @Binds
    abstract fun bindRepository(repository: UsersRepositoryImpl): UsersRepository

}