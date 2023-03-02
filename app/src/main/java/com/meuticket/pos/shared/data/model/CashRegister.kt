package com.meuticket.pos.shared.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class CashRegister(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    @ColumnInfo val event_uid: Int,
    @ColumnInfo val open_cash: Double,
) {
    @ColumnInfo var closeDate: Long? = null
    @ColumnInfo var openDate: Long = Date().time

    @ColumnInfo var cashTotal: Double = 0.0
    @ColumnInfo var cardTotal: Double = 0.0
    @ColumnInfo var cortesyTotal: Double = 0.0
    @ColumnInfo var pixTotal: Double = 0.0
    @ColumnInfo var otherTotal: Double = 0.0

    @ColumnInfo var withdrawTotal: Double = 0.0
    @ColumnInfo var inputsTotal: Double = 0.0
    @ColumnInfo var rollbackTotal: Double = 0.0

}
