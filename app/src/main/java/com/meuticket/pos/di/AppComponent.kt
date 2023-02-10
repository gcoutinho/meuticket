package com.meuticket.pos.di

import android.app.Application
import com.meuticket.pos.CustomApplication
import com.meuticket.pos.core.background.di.WorkersModule
import com.meuticket.pos.core.storage.di.RoomModule
import com.meuticket.pos.home.di.HomeModuleBuilder
import com.meuticket.pos.register.di.RegisterModuleBuilder
import com.meuticket.pos.login.di.LoginModuleBuilder
import com.meuticket.pos.payment.di.PaymentModuleBuilder
import com.meuticket.pos.shared.di.CategoryModule
import com.meuticket.pos.shared.di.EventModule
import com.meuticket.pos.shared.di.ProductsModule
import com.meuticket.pos.shared.di.UsersModule
import com.meuticket.pos.ticket.di.NewTicketModuleBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        WorkersModule::class,
        RoomModule::class,

        LoginModuleBuilder::class,
        HomeModuleBuilder::class,
        NewTicketModuleBuilder::class,
        PaymentModuleBuilder::class,
        RegisterModuleBuilder::class,

        ProductsModule::class,
        UsersModule::class,
        CategoryModule::class,
        EventModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: CustomApplication)

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
