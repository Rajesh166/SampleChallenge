package com.example.sample.repository

import com.example.sample.helper.BaseTest
import com.example.sample.network.ApiServices
import com.example.sample.response.Restaurant
import com.example.sample.response.Result
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

@ExperimentalCoroutinesApi
class RestaurantRepositoryTest: BaseTest() {

    @MockK
    private lateinit var apiServices: ApiServices

    private lateinit var repo: RestaurantRepository

    private val listResponse: List<Restaurant> =
            (0 until 10).map { Restaurant(id = it.toLong(), description = "Title of $it") }

    override fun setup() {
        super.setup()
        repo = RestaurantRepository(apiServices)
    }

    @Test
    fun `Get Restaurant List`() {
        // Given
        val response = Response.success(listResponse)

        //given
        coEvery {
            apiServices.fetchRestaurants(any(), any())
        } returns mockResult()

        runBlockingTest {
            // When
            val result = repo.fetchRestaurants()
            when(result) {
                // Then
                is Result.Success -> Assert.assertEquals(response.body()?.size, result.data.size)
            }
        }
    }

    @Test
    fun `Get Restaurant Empty List`() {
        //given
        coEvery {
            apiServices.fetchRestaurants(any(), any())
        } returns mockEmptyResult()

        runBlockingTest {
            // When
            val result = repo.fetchRestaurants()
            when(result) {
                // Then
                is Result.Success -> Assert.assertTrue(result.data.isEmpty())
            }
        }
    }

    @Test
    fun `Get Restaurant Details`() {

        val response = Response.success(Restaurant())
        //given
        coEvery {
            apiServices.getRestaurantDetails(any())
        } returns mockRestaurant()

        runBlockingTest {
            when(val result = repo.getRestaurantDetails(Mockito.anyLong())) {
                is Result.Success ->  Assert.assertEquals(response.body(), result.data)
            }

        }
    }

    private fun mockResult() : Response<List<Restaurant>> {
        return Response.success(listResponse)
    }

    private fun mockEmptyResult() : Response<List<Restaurant>> {
        return Response.success(emptyList())
    }

    private fun mockRestaurant() : Response<Restaurant> {
        return Response.success(Restaurant())
    }
}