package com.meuticket.pos.register.event.di

import androidx.lifecycle.ViewModel
import com.meuticket.pos.base.ViewModelKey
import com.meuticket.pos.register.event.presentation.EventFormViewModel
import com.meuticket.pos.register.event.presentation.EventRegisterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class EventRegisterModule {

    @Binds
    @IntoMap
    @ViewModelKey(EventRegisterViewModel::class)
    abstract fun bindEventRegisterViewModel(viewModel: EventRegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EventFormViewModel::class)
    abstract fun bindEventFormViewModel(viewModel: EventFormViewModel): ViewModel
}