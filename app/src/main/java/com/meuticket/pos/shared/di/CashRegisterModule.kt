package com.meuticket.pos.shared.di

import com.meuticket.pos.shared.data.CashRepository
import com.meuticket.pos.shared.data.CashRepositoryImpl
import com.meuticket.pos.shared.domain.CashInteractor
import com.meuticket.pos.shared.domain.CashInteractorImpl
import dagger.Binds
import dagger.Module

@Module
abstract class CashRegisterModule {

    @Binds
    abstract fun bindInteractor(interactor: CashInteractorImpl): CashInteractor

    @Binds
    abstract fun bindRepository(repository: CashRepositoryImpl): CashRepository

}