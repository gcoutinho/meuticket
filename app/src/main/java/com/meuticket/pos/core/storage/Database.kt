package com.meuticket.pos.core.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meuticket.pos.core.storage.dao.*
import com.meuticket.pos.shared.data.model.*

@Database(entities = [User::class, Product::class, Category::class, Event::class, CashRegister::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun cashRegisterDao(): CashRegisterDao
    abstract fun eventDao(): EventDao
}