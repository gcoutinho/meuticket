package com.meuticket.pos.payment.di

import androidx.lifecycle.ViewModel
import com.meuticket.pos.base.ViewModelKey
import com.meuticket.pos.payment.presentation.PaymentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PaymentModule {

    @Binds
    @IntoMap
    @ViewModelKey(PaymentViewModel::class)
    abstract fun bindViewModel(viewModel: PaymentViewModel): ViewModel
}