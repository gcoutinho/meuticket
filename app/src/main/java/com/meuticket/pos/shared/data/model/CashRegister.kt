package com.meuticket.pos.shared.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity
class CashRegister(
    @PrimaryKey
    val uid: String,
    @ColumnInfo val event_uid: String,
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
    @ColumnInfo var status: Int = 0

    @Ignore
    var order: Order? = null
}
