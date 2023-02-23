package com.meuticket.pos.cart.di

import com.meuticket.pos.cart.presentation.CartActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CartModuleBuilder {

    @ContributesAndroidInjector(modules = [CartModule::class])
    abstract fun bindCartActivity(): CartActivity
}