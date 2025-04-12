package com.doaa.mazaadytask.core.base



sealed class ResponsResult<out T>(
    val data: T? = null,
    val message: String? = null,

) {
    class Success<T>(data: T): ResponsResult<T>(data)
    class Error<T>(message: String, data: T? = null): ResponsResult<T>(data, message)
    class Loading<T>(data: T? = null): ResponsResult<T>(data)

}

