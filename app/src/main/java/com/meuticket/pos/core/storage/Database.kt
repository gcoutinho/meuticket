package com.meuticket.pos.core.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meuticket.pos.core.storage.dao.UserDao
import com.meuticket.pos.shared.data.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}