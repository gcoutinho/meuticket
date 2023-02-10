package com.meuticket.pos.shared.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey val uid: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val image: String,
    @ColumnInfo val value: Double,
    @ColumnInfo val category_uid: Int
): java.io.Serializable {
    var qtd: Int = 0

    @ColumnInfo
    var secondWay: Boolean = false

    @Ignore
    lateinit var category: Category
}
