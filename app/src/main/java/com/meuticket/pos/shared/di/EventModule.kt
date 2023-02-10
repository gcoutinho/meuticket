package com.meuticket.pos.shared.di

import com.meuticket.pos.shared.data.EventRepository
import com.meuticket.pos.shared.data.EventRepositoryImpl
import com.meuticket.pos.shared.domain.EventListInteractor
import com.meuticket.pos.shared.domain.EventListInteractorImpl
import dagger.Binds
import dagger.Module

@Module
abstract class EventModule {

    @Binds
    abstract fun bindInteractor(interactor: EventListInteractorImpl): EventListInteractor

    @Binds
    abstract fun bindRepository(repository: EventRepositoryImpl): EventRepository

}