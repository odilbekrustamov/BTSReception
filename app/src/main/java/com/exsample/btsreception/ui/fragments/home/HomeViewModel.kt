package com.exsample.btsreception.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exsample.btsreception.model.ClientList
import com.exsample.btsreception.repository.MainRepository
import com.exsample.readnumbercall.model.CallNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject
constructor(private val repository: MainRepository): ViewModel(){

    val clientListsFromDB = MutableLiveData<List<ClientList>>()

    fun insertClientNumberToDB(clientList: ClientList){
        viewModelScope.launch {
            repository.insertClientNumberToDB(clientList)
        }
    }

    fun getClient() =
        viewModelScope.launch {
            val clientLists = repository.getClient()
            clientListsFromDB.postValue(clientLists)
        }

}