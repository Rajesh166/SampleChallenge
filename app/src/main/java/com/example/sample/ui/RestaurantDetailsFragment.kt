package com.example.sample.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sample.R
import com.example.sample.databinding.FragmentRestaurantDetailsBinding
import com.example.sample.network.RestaurantApiClient
import com.example.sample.repository.RestaurantRepository
import com.example.sample.response.EventObserver
import com.example.sample.response.Restaurant
import com.example.sample.viewmodels.RestaurantDetailsViewModel

class RestaurantDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRestaurantDetailsBinding
    private lateinit var restaurant: Restaurant

    private val viewModel: RestaurantDetailsViewModel by viewModels {
        RestaurantDetailsViewModelFactory(RestaurantRepository(RestaurantApiClient.apiServices), restaurant)
    }

    companion object {

        const val FRAGMENT_TAG = "RESTAURANT_DETAILS_FRAGMENT"
        const val EXTRA_RESTAURANT_DATA = "RESTAURANT_DATA"

        fun getInstance(restaurant: Restaurant): RestaurantDetailsFragment {
            return RestaurantDetailsFragment().apply {
                this.restaurant = restaurant
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_restaurant_details, container, false)

        if (savedInstanceState != null) {
            restaurant = savedInstanceState.getSerializable(EXTRA_RESTAURANT_DATA) as Restaurant
            hideLoading()
        }

        binding.restaurant = restaurant
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        initView()

        return binding.root
    }

    private fun initView() {

        viewModel.getEvents().observe(viewLifecycleOwner, EventObserver {
            when (it) {
                is RestaurantDetailsViewModel.ViewEvent.FinishedLoading -> {
                    hideLoading()
                }
                is RestaurantDetailsViewModel.ViewEvent.ShowError -> {
                    showError(it.errorMsg)
                }
            }
        })

        binding.retry.setOnClickListener {
            viewModel.retryLoading()
        }

        binding.back.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.root.let {
            it.isFocusableInTouchMode = true
            it.requestFocus()
            it.setOnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    (context as FragmentActivity).supportFragmentManager.popBackStack()
                    true
                } else
                    false
            }
        }
    }

    private fun hideLoading() {
        binding.apply {
            loading.visibility = View.GONE
            msg.visibility = View.GONE
            retry.visibility = View.GONE
        }
    }

    private fun showError(message: String) {
        hideLoading()
        binding.apply {
            msg.visibility = View.VISIBLE
            msg.text = message
            retry.visibility = View.VISIBLE
        }

    }

    class RestaurantDetailsViewModelFactory(private val repo: RestaurantRepository, private val restaurant: Restaurant) :
            ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RestaurantDetailsViewModel(repo, restaurant) as T
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(EXTRA_RESTAURANT_DATA, restaurant)
    }

}