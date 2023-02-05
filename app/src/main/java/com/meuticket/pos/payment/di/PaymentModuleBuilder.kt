package com.meuticket.pos.payment.di

import com.meuticket.pos.payment.presentation.PaymentActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PaymentModuleBuilder {

    @ContributesAndroidInjector(modules = [PaymentModule::class])
    abstract fun bindPaymentActivity(): PaymentActivity
}