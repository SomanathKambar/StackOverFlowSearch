package com.somanath.stackoverflowsearch.data

sealed class ApiResponse<out T : Any> {
    object Loading: ApiResponse<Nothing>()
    data class Success<out T : Any>(val data: T) : ApiResponse<T>()
    data class Failure(val exception: Throwable) : ApiResponse<Nothing>()


    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading"
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Error[exception=$exception]"
        }
    }
}
