package com.example.sample.helper

import androidx.lifecycle.Observer
import com.example.sample.response.Result
import io.mockk.spyk

object TestHelper {
    fun <T> getMockStateObserver(): androidx.lifecycle.Observer<Result<T>> = spyk(Observer { })
    fun <T> getMockObserver(): androidx.lifecycle.Observer<T> = spyk(androidx.lifecycle.Observer { })
}