package com.exsample.btsreception.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clint_table")
data class ClientList (
    @PrimaryKey
    @ColumnInfo(name = "number") val number: String
)