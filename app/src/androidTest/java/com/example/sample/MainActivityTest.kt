package com.example.sample

import android.content.Context
import android.view.View
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.example.sample.network.RestaurantApiClient
import com.example.sample.repository.RestaurantRepository
import com.example.sample.ui.MainActivity
import com.example.sample.ui.RestaurantListFragment
import junit.framework.Assert.fail
import kotlinx.android.synthetic.main.fragment_restaurant_list.*
import kotlinx.coroutines.runBlocking
import org.junit.*


class MainActivityTest {

    @get:Rule
    var rule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    private lateinit var appRepository: RestaurantRepository
    private lateinit var instrumentationCtx: Context
    private var mainActivity: MainActivity? = null

    @Before
    fun setUp() {
        mainActivity =  rule.activity
        instrumentationCtx = getInstrumentation().context
    }

    @Test
    fun activityLaunch(){
        mainActivity?.let {
            if (it.findViewById<View>(R.id.container) != null) assert(true)
        } ?: fail()
    }

    @Test
    fun fragmentLaunch(){
        mainActivity?.let { mainActivity ->
            mainActivity.findViewById<View>(R.id.container)?.let {
                val fragment = RestaurantListFragment()
                mainActivity.addFragment(fragment)
                getInstrumentation().waitForIdleSync()
                fragment.rv_restaurant_list?.let {
                    if (it.findViewById<View>(R.id.rv_restaurant_list) != null) assert(true) else fail()
                }
            }
        } ?: fail()
    }

    @Test
    fun getNearByRestaurants() = runBlocking {
        appRepository = RestaurantRepository(RestaurantApiClient.apiServices)
        val result = appRepository.fetchRestaurants(37.422740, -122.139956)
        Assert.assertNotNull(result)
    }

    @After
    fun tearDown() {
        mainActivity = null
    }
}