package com.meuticket.pos.register.di

import com.meuticket.pos.register.category.di.CategoryRegisterModule
import com.meuticket.pos.register.category.presentation.CategoryFormActivity
import com.meuticket.pos.register.category.presentation.CategoryRegisterActivity
import com.meuticket.pos.register.presentation.RegisterActivity
import com.meuticket.pos.register.products.di.ProductsRegisterModule
import com.meuticket.pos.register.products.presentation.ProductFormActivity
import com.meuticket.pos.register.products.presentation.ProductsRegisterActivity
import com.meuticket.pos.register.users.di.UsersRegisterModule
import com.meuticket.pos.register.users.presentation.UserFormActivity
import com.meuticket.pos.register.users.presentation.UsersRegisterActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RegisterModuleBuilder {

    @ContributesAndroidInjector(modules = [UsersRegisterModule::class])
    abstract fun bindUserFormActivity(): UserFormActivity

    @ContributesAndroidInjector(modules = [UsersRegisterModule::class])
    abstract fun bindUsersRegisterActivity(): UsersRegisterActivity

    @ContributesAndroidInjector(modules = [ProductsRegisterModule::class])
    abstract fun bindProductFormActivity(): ProductFormActivity

    @ContributesAndroidInjector(modules = [ProductsRegisterModule::class])
    abstract fun bindProductsRegisterActivity(): ProductsRegisterActivity

    @ContributesAndroidInjector(modules = [CategoryRegisterModule::class])
    abstract fun bindCategoryFormActivity(): CategoryFormActivity

    @ContributesAndroidInjector(modules = [CategoryRegisterModule::class])
    abstract fun bindCategoryRegisterActivity(): CategoryRegisterActivity

    @ContributesAndroidInjector(modules = [RegisterModule::class])
    abstract fun bindRegisterActivity(): RegisterActivity
}