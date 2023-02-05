package com.meuticket.pos.shared.di

import com.meuticket.pos.shared.data.ProductsRepository
import com.meuticket.pos.shared.data.ProductsRepositoryImpl
import com.meuticket.pos.shared.domain.ProductsListInteractor
import com.meuticket.pos.shared.domain.ProductsListInteractorImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ProductsModule {

    @Binds
    abstract fun bindInteractor(interactor: ProductsListInteractorImpl): ProductsListInteractor

    @Binds
    abstract fun bindRepository(repository: ProductsRepositoryImpl): ProductsRepository

}