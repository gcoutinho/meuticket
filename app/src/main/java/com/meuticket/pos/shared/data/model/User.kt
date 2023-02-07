package com.meuticket.pos.shared.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val password: String,
    @ColumnInfo val admin: Boolean
)
