/*

package com.example.sample.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.sample.helper.BaseTest
import com.example.sample.repository.RestaurantRepository
import com.example.sample.response.Restaurant
import com.example.sample.response.Result
import com.example.sample.viewmodels.RestaurantDetailsViewModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RestaurantDetailsViewModelTest: BaseTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var viewModel: RestaurantDetailsViewModel

    @MockK
    private lateinit var restaurantRepository: RestaurantRepository

    @Before
    fun before() {
        val restaurant = Restaurant()
        viewModel = RestaurantDetailsViewModel(restaurantRepository, restaurant)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getRestaurantDataTest()  {

        //given
        coEvery {
            restaurantRepository.getRestaurantDetails(any())
        } returns mockResult()

        runBlockingTest {
            //when
            viewModel.retryLoading()
            Assert.assertNotNull(viewModel.getDetailsData().value)
        }


    }

    private fun mockResult() : Result.Success<Restaurant> {
        val restaurant = Restaurant()
        restaurant.id = 30
        return Result.Success(restaurant)
    }

}
*/
