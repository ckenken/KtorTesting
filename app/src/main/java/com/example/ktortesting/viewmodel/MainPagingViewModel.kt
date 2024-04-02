package com.example.ktortesting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.ktortesting.struct.School
import com.example.ktortesting.struct.SchoolPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainPagingViewModel: ViewModel() {

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