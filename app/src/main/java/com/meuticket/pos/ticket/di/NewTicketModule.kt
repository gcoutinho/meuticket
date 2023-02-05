package com.meuticket.pos.ticket.di

import androidx.lifecycle.ViewModel
import com.meuticket.pos.base.ViewModelKey
import com.meuticket.pos.shared.domain.ProductsListInteractor
import com.meuticket.pos.shared.domain.ProductsListInteractorImpl
import com.meuticket.pos.ticket.presentation.NewTicketViewModel
import com.meuticket.pos.ticket.presentation.ProductListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class NewTicketModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewTicketViewModel::class)
    abstract fun bindsViewModel(viewModel: NewTicketViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductListViewModel::class)
    abstract fun bindsProductViewModel(viewModel: ProductListViewModel): ViewModel

}