package com.example.sample.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample.repository.RestaurantRepository
import com.example.sample.response.Event
import com.example.sample.response.Restaurant
import kotlinx.coroutines.launch
import com.example.sample.response.Result


class RestaurantViewModel(private val restaurantRepository: RestaurantRepository) : ViewModel() {

    private val restaurantsList: MutableLiveData<List<Restaurant>> by lazy {
        MutableLiveData<List<Restaurant>>().also {
            loadRestaurants()
        }
    }

    private val event: MutableLiveData<Event<ViewEvent>> by lazy {
        MutableLiveData<Event<ViewEvent>>()
    }

    fun getEvents(): LiveData<Event<ViewEvent>> {
        return event
    }

    fun retryLoading() {
        loadRestaurants()
    }

    fun fetchRestaurants(): LiveData<List<Restaurant>>? {
        return restaurantsList
    }

    private fun loadRestaurants() {
        viewModelScope.launch {
            val result = restaurantRepository.fetchRestaurants()
            when (result) {
                is Result.Success -> {
                    result.data.let {
                        restaurantsList.value = it.toList()
                        event.value = Event(ViewEvent.FinishedLoading)
                    }
                }
                is Result.Error -> {
                    event.value = Event(ViewEvent.ShowError(result.message))
                }
            }
        }
    }

    sealed class ViewEvent {
        data class NavigateToDetail(val data: Restaurant) : ViewEvent()
        data class ShowError(val errorMsg: String) : ViewEvent()
        object FinishedLoading : ViewEvent()
    }

    fun onRestaurantItemClick(restaurant: Restaurant) {
        event.value = Event(ViewEvent.NavigateToDetail(restaurant))
    }
}