package com.meuticket.pos.core.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meuticket.pos.core.storage.dao.CategoryDao
import com.meuticket.pos.core.storage.dao.EventDao
import com.meuticket.pos.core.storage.dao.ProductDao
import com.meuticket.pos.core.storage.dao.UserDao
import com.meuticket.pos.shared.data.model.Category
import com.meuticket.pos.shared.data.model.Event
import com.meuticket.pos.shared.data.model.Product
import com.meuticket.pos.shared.data.model.User

@Database(entities = [User::class, Product::class, Category::class, Event::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun eventDao(): EventDao
}