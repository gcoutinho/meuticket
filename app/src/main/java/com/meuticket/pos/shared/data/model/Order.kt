package com.meuticket.pos.shared.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Order(
    @PrimaryKey val uid: String,
    @ColumnInfo val cashId: String,
    @ColumnInfo val status: Int,
    @ColumnInfo val date: Long
) {

    @ColumnInfo var total: Double = 0.0

    @Ignore
    var payments: ArrayList<OrderPayment> = ArrayList()

    class OrderPayment(
        val value: Double,
        val mode: Mode
    ) {
        sealed class Mode {
            object Cash: Mode()
            object Pix: Mode()
            object Credit: Mode()
            object Debit: Mode()
            object Cortesy: Mode()
            object Other: Mode()
        }
    }
}