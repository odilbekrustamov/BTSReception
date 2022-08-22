package com.exsample.btsreception.repository

import com.exsample.btsreception.model.ClientList
import com.exsample.btsreception.networking.UserServer
import com.exsample.readnumbercall.database.CallDao
import com.exsample.readnumbercall.model.CallNumber
import javax.inject.Inject

class MainRepository @Inject constructor(private val callDao: CallDao, private val server: UserServer) {

    /**
     * Room
     */
    suspend fun insertCallNumberToDB(callNumber: CallNumber) = callDao.insertCallNumberToDB(callNumber)
    suspend fun getItems(number: String) = callDao.getItems(number)

    suspend fun insertClientNumberToDB(clientList: ClientList) = callDao.insertClientNumberToDB(clientList)
    suspend fun getClient() = callDao.getClients()
    suspend fun getClientNumber(number: String) = callDao.getClientNumber(number)

}