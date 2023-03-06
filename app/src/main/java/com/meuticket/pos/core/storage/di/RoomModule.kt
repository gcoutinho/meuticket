package com.meuticket.pos.core.storage.di

import android.app.Application
import androidx.room.Room
import com.meuticket.pos.core.storage.AppDatabase
import com.meuticket.pos.core.storage.dao.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    @Singleton
    @Provides
    fun providesRoomDatabase(application: Application?): AppDatabase {
        return Room.databaseBuilder(application!!, AppDatabase::class.java, "ticket-db").build()
    }

    @Singleton
    @Provides
    fun providesUserDao(AppDatabase: AppDatabase): UserDao {
        return AppDatabase.userDao()
    }

    @Singleton
    @Provides
    fun providesProductDao(AppDatabase: AppDatabase): ProductDao {
        return AppDatabase.productDao()
    }

    @Singleton
    @Provides
    fun providesCategoryDao(AppDatabase: AppDatabase): CategoryDao {
        return AppDatabase.categoryDao()
    }

    @Singleton
    @Provides
    fun providesCashRegisterDao(AppDatabase: AppDatabase): CashRegisterDao {
        return AppDatabase.cashRegisterDao()
    }

    @Singleton
    @Provides
    fun providesEventDao(AppDatabase: AppDatabase): EventDao {
        return AppDatabase.eventDao()
    }
}