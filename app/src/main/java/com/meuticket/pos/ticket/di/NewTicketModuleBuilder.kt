package com.meuticket.pos.ticket.di

import com.meuticket.pos.ticket.presentation.NewTicketActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NewTicketModuleBuilder {

    @ContributesAndroidInjector(modules = [NewTicketModule::class])
    abstract fun bindNewTicketActivity(): NewTicketActivity
}