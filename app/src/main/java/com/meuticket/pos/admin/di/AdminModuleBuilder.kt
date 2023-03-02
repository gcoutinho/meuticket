package com.meuticket.pos.admin.di

import com.meuticket.pos.admin.presentation.AdminActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AdminModuleBuilder {

    @ContributesAndroidInjector(modules = [AdminModule::class])
    abstract fun bindAdminActivity(): AdminActivity
}