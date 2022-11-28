package com.example.ktortesting.struct

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ktortesting.datamodel.ApiRepository2
import com.example.ktortesting.datamodel.RequestResult
import java.lang.Integer.max

class SchoolPagingSource : PagingSource<String, School>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, School> {
        val nextKey = params.key ?: "0:30"

        val schools = ApiRepository2.getSchools(
            country = "JP",
            lang = "ja-jp",
            withPetition = true,
            nextKey = nextKey, // "0:30", "30:30", "60:30", ...
        )

        val start = nextKey.split(":")[0].toInt()
        Log.d("ckenken", "start = $start")

        return when (schools) {
            is RequestResult.Success -> {
                LoadResult.Page(
                    data = schools.result.items,
                    prevKey = if (nextKey == "0:30") null else "${start - 30}:30",
                    nextKey = schools.result.nextKey,
                )
            }
            is RequestResult.Failed -> {
                LoadResult.Error(schools.error)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<String, School>): String? {
        val key = state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.let {
                "${it.split(":")[0].toInt() + 30}:30"
            } ?: state.closestPageToPosition(anchorPosition)?.nextKey?.let {
                "${max(it.split(":")[0].toInt() - 30, 0)}:30"
            }
        }
        Log.d("ckenken", "getRefreshKey() key = $key")
        return key
    }
}