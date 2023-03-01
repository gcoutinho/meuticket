package com.meuticket.pos.core.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.meuticket.pos.shared.data.model.Event

@Dao
interface EventDao {
    @Query("SELECT * FROM event")
    fun getAll(): List<Event>

    @Query("SELECT * FROM event WHERE uid IN (:eventIds)")
    fun loadAllByIds(eventIds: IntArray): List<Event>

    @Query("SELECT * FROM event WHERE name LIKE :first")
    fun findByName(first: String): List<Event>

    @Insert(onConflict = REPLACE)
    fun insertAll(events: List<Event>)

    @Delete
    fun delete(event: Event)
    @Insert(onConflict = REPLACE)
    fun insertOrUpdate(event: Event)
}