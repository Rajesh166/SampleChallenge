package com.example.sample.model

import com.example.sample.response.Restaurant
import org.junit.Assert
import org.junit.Test

class RestaurantTest {

    @Test
    fun displayStatusUpdateTest() {
        val restaurant = Restaurant()
        restaurant.statusType = "Closed"
        restaurant.status = "23 mins"
        Assert.assertEquals("Closed", restaurant.statusUpdate)

        restaurant.statusType = "open"
        Assert.assertEquals("23 mins", restaurant.statusUpdate)

        restaurant.statusType = "randomString"
        Assert.assertEquals("23 mins", restaurant.statusUpdate)

        restaurant.statusType = "Open"
        Assert.assertEquals("23 mins", restaurant.statusUpdate)

        restaurant.statusType = "Open"
        restaurant.status = ""
        Assert.assertEquals("", restaurant.statusUpdate)

        restaurant.statusType = ""
        restaurant.status = "48 mins"
        Assert.assertEquals("48 mins", restaurant.statusUpdate)
    }

    @Test
    fun displayRatingTest() {
        val restaurant = Restaurant()
        restaurant.rating = 4.5
        restaurant.numberOfRatings = 300
        Assert.assertEquals(4.5, restaurant.rating, 0.0)
        Assert.assertEquals(300, restaurant.numberOfRatings)
        Assert.assertEquals("4.5 (300 Users)", restaurant.ratingUpdate)

        restaurant.rating = 1.5
        restaurant.numberOfRatings = 10000000
        Assert.assertEquals(1.5, restaurant.rating, 0.0)
        Assert.assertEquals(10000000, restaurant.numberOfRatings)
        Assert.assertEquals("1.5 (10000000 Users)", restaurant.ratingUpdate)

        restaurant.rating = 1.5
        restaurant.numberOfRatings = 0
        Assert.assertEquals(1.5, restaurant.rating, 0.0)
        Assert.assertEquals(0, restaurant.numberOfRatings)
        Assert.assertEquals("1.5", restaurant.ratingUpdate)

        restaurant.rating = 1.5
        restaurant.numberOfRatings = -1
        Assert.assertEquals(1.5, restaurant.rating, 0.0)
        Assert.assertEquals(-1, restaurant.numberOfRatings)
        Assert.assertEquals("1.5", restaurant.ratingUpdate)

    }
}