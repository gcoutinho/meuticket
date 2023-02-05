package com.meuticket.pos.shared.di

import com.meuticket.pos.shared.data.CategoryRepository
import com.meuticket.pos.shared.data.CategoryRepositoryImpl
import com.meuticket.pos.shared.domain.CategoryListInteractor
import com.meuticket.pos.shared.domain.CategoryListInteractorImpl
import dagger.Binds
import dagger.Module

@Module
abstract class CategoryModule {

    @Binds
    abstract fun bindInteractor(interactor: CategoryListInteractorImpl): CategoryListInteractor

    @Binds
    abstract fun bindRepository(repository: CategoryRepositoryImpl): CategoryRepository

}