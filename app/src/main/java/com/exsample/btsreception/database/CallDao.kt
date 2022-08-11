package com.exsample.readnumbercall.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exsample.btsreception.model.ClientList
import com.exsample.readnumbercall.model.CallNumber

@Dao
interface CallDao {
    @Insert
    suspend fun insertCallNumberToDB(callNumber: CallNumber)

    @Query("SELECT * FROM call_table WHERE number = :number")
    suspend fun getItems(number: String): List<CallNumber>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClientNumberToDB(clientList: ClientList)

    @Query("SELECT * FROM clint_table")
    suspend fun getClients(): List<ClientList>

    @Query("SELECT EXISTS(SELECT * FROM clint_table WHERE number = :number)")
    suspend fun getClientNumber(number: String): Boolean
}