
package com.example.sample.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.sample.helper.BaseTest
import com.example.sample.repository.RestaurantRepository
import com.example.sample.response.Restaurant
import com.example.sample.response.Result
import com.example.sample.viewmodels.RestaurantListViewModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class RestaurantListViewModelTest: BaseTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var viewModel: RestaurantListViewModel
    @MockK
    private lateinit var restaurantRepository: RestaurantRepository

    private val listResponse: List<Restaurant> =
            (0 until 10).map { Restaurant(id = it.toLong(), description = "Title of $it") }

    @Before
    fun before() {
        viewModel = RestaurantListViewModel(restaurantRepository)
    }

    @Test
    fun `fetch Restaurant List`() {

        val response = Response.success(listResponse)

        //given
        coEvery {
            restaurantRepository.fetchRestaurants()
        } returns mockResult()

        runBlockingTest {
            // When
            viewModel.retryLoading()
        }

        Assert.assertEquals(response.body(), viewModel.getRestaurantList().value)
    }

    private fun mockResult() : Result<List<Restaurant>> {
        return Result.Success(listResponse)
    }

}
