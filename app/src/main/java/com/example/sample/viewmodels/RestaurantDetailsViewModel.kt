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

class RestaurantDetailsViewModel(private val restaurantRepository: RestaurantRepository, val restaurant: Restaurant) : ViewModel() {

    private val restaurantDetails: MutableLiveData<Restaurant> by lazy {
        MutableLiveData<Restaurant>().also {
            loadRestaurantDetailsData()
        }
    }

    private val event: MutableLiveData<Event<ViewEvent>> by lazy {
        MutableLiveData<Event<ViewEvent>>()
    }

    fun getDetailsData(): LiveData<Restaurant> = restaurantDetails

    fun getEvents(): LiveData<Event<ViewEvent>> {
        return event
    }

    fun retryLoading() {
        loadRestaurantDetailsData()
    }

    private fun loadRestaurantDetailsData() {
        viewModelScope.launch {
            when (val synopsisResult =  restaurantRepository.getRestaurantDetails(restaurant.id)) {
                is Result.Success -> {
                    restaurantDetails.value = synopsisResult.data
                    event.value = Event(ViewEvent.FinishedLoading)
                }
                is Result.Error -> {
                    event.value = Event(ViewEvent.ShowError(synopsisResult.message))
                }
            }
        }
    }

    sealed class ViewEvent {
        data class ShowError(val errorMsg: String) : ViewEvent()
        object FinishedLoading : ViewEvent()
    }

}