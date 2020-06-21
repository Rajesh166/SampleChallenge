package com.example.sample.response

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(var message: String) : Result<Nothing>()
}