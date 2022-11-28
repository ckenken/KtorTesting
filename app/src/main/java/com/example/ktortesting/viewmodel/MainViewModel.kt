package com.example.ktortesting.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktortesting.datamodel.*
import com.example.ktortesting.struct.School
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class  MainViewModel : ViewModel() {

    val pageData = MutableLiveData<RequestState<List<School>>>()

    fun getSchools() {
        pageData.value = RequestState.Loading()
        viewModelScope.launch {
            pageData.value = withContext(Dispatchers.IO) {
                ApiRepository2.getSchools(
                    country = "JP",
                    lang = "ja-jp",
                    withPetition = true,
                    nextKey = "0:600",
                )
            }.let {
                when (it) {
                    is RequestResult.Success -> {
                        RequestState.Success(it.result.items)
                    }
                    is RequestResult.Failed -> {
                        RequestState.Error(it.error)
                    }
                }
            }
        }
    }
}