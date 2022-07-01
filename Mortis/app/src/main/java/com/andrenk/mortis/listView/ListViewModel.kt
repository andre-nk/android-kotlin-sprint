package com.andrenk.mortis.listView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrenk.mortis.network.ApiService
import com.andrenk.mortis.network.PeopleModel
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val _peopleList = MutableLiveData<List<PeopleModel>>()
    private val _errorStatus = MutableLiveData<String>()

    val peopleList: LiveData<List<PeopleModel>> = _peopleList
    val errorStatus: LiveData<String> = _errorStatus

    init {
        getPeople()
    }

    private fun getPeople() {
        viewModelScope.launch {
            try {
                val mockResponse = ApiService.retrofitService.getPeople()
                _peopleList.value = mockResponse.results
            } catch (e: Exception){
                _errorStatus.value = e.message
            }
        }
    }
}