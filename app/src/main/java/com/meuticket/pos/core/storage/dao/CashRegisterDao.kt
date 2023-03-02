package com.meuticket.pos.core.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.meuticket.pos.shared.data.model.CashRegister

@Dao
interface CashRegisterDao {
    @Query("SELECT * FROM cashRegister")
    fun getAll(): List<CashRegister>

    @Query("SELECT * FROM cashRegister WHERE uid IN (:cashRegisterIds)")
    fun loadAllByIds(cashRegisterIds: IntArray): List<CashRegister>

    @Query("SELECT * FROM cashRegister ORDER BY uid DESC LIMIT 1")
    fun findLast(): CashRegister?

    @Insert(onConflict = REPLACE)
    fun insertAll(cashRegisters: List<CashRegister>)

    @Insert(onConflict = REPLACE)
    fun save(cashRegister: CashRegister)
}