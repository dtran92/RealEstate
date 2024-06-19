package com.dtran.real_estate_compose.data

sealed class Response<T>(val data: T? = null, val error: Throwable? = null) {
    class Success<T>(data: T) : Response<T>(data = data)
    class Loading<T> : Response<T>()
    class Error<T>(error: Throwable?) : Response<T>(error = error)
}