package com.example.sample.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sample.repository.RestaurantRepository

class RestaurantListViewModelFactory(private val repo: RestaurantRepository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>) =
                RestaurantListViewModel(repo) as T
    }