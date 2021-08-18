package com.shigure.lastfm_api.core.remote

@Suppress("unused")
sealed class ResponseResult<T> {
    class Success<T>(val data: T) : ResponseResult<T>()
    class Failure<T>(val message: String) : ResponseResult<T>()
    class Empty<T> : ResponseResult<T>()
}
