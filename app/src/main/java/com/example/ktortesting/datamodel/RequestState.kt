package com.example.ktortesting.datamodel

sealed class RequestState<out T> {

    abstract val data: T?

    data class Success<T>(override val data: T) : RequestState<T>()
    data class Loading<T>(override val data: T? = null) : RequestState<T>()
    data class Error(val throwable: Throwable) : RequestState<Nothing>() {
        override val data = null
    }

    fun <R> map(transform: (T) -> R) = when (this) {
        is Success -> Success(transform(data))
        is Loading -> Loading(data?.let(transform))
        is Error -> Error(throwable)
    }
}

val <T> RequestState<T>?.isSuccess get() = this is RequestState.Success
val <T> RequestState<T>?.isLoading get() = this is RequestState.Loading
val <T> RequestState<T>?.isError get() = this is RequestState.Error