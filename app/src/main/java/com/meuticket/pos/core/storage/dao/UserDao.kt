package com.meuticket.pos.core.storage.dao

import androidx.room.*
import com.meuticket.pos.shared.data.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE name LIKE :first LIMIT 1")
    fun findByName(first: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM user WHERE name LIKE :user and password LIKE :password LIMIT 1")
    fun findByNamePassword(user: String, password: String): User?
}