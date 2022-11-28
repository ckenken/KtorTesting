package com.example.ktortesting.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.ktortesting.struct.School
import com.example.ktortesting.struct.SchoolPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class MainPagingViewModel : ViewModel() {

    val pageData = getData()

    private fun getData(): Flow<PagingData<School>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false,
            )
        ) {
            SchoolPagingSource()
        }.flow.flowOn(Dispatchers.IO).cachedIn(viewModelScope)
    }
}