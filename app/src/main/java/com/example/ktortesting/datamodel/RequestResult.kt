package com.example.ktortesting.datamodel

sealed class RequestResult<out T> {
    class Success<T>(val result: T) : RequestResult<T>()
    class Failed(val error: Throwable) : RequestResult<Nothing>()
}

fun <T> RequestResult<T>.toRequestState(): RequestState<T> = when (this) {
    is RequestResult.Success -> RequestState.Success(result)
    is RequestResult.Failed -> RequestState.Error(error)
}