package com.example.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sample.ui.RestaurantListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, RestaurantListFragment())
            transaction.commit()
        }
    }

}