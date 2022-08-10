package com.exsample.readnumbercall.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "call_table")
data class CallNumber (
    @PrimaryKey
    @ColumnInfo(name = "call_time") val callTime: String,
    @ColumnInfo(name = "number") val number: String,
    @ColumnInfo(name = "time_spent") val timeSpent: String,
    @ColumnInfo(name = "call_type") val callType: String,
    @ColumnInfo(name = "isSent") val isSent: Boolean
)