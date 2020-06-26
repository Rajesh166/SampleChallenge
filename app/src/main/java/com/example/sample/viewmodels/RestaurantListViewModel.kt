package com.example.sample.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample.repository.RestaurantRepository
import com.example.sample.response.Event
import com.example.sample.response.Restaurant
import com.example.sample.response.Result
import kotlinx.coroutines.launch


class RestaurantListViewModel(private val restaurantRepository: RestaurantRepository) : ViewModel() {

    private var restaurantsList: MutableLiveData<List<Restaurant>> = MutableLiveData()

    private val event: MutableLiveData<Event<ViewEvent>> by lazy {
        MutableLiveData<Event<ViewEvent>>()
    }

    fun getRestaurantList(): LiveData<List<Restaurant>> = restaurantsList

    fun getEvents(): LiveData<Event<ViewEvent>> {
        return event
    }

    fun fetchRestaurants(): LiveData<List<Restaurant>>? {
        return restaurantsList
    }

    fun loadRestaurants() {
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