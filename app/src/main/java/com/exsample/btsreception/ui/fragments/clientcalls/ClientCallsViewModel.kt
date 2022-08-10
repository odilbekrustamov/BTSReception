package com.exsample.btsreception.ui.fragments.clientcalls

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
class ClientCallsViewModel @Inject
constructor(private val repository: MainRepository): ViewModel() {

    val callNumbersFromDB = MutableLiveData<List<CallNumber>>()

    fun getItems(number: String) =
        viewModelScope.launch {
            val clientLists = repository.getItems(number)
            callNumbersFromDB.postValue(clientLists)
        }

}