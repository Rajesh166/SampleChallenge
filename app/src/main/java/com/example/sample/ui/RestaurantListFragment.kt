package com.example.sample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sample.R
import com.example.sample.adapters.RestaurantListAdapter
import com.example.sample.databinding.FragmentRestaurantListBinding
import com.example.sample.network.RestaurantApiClient
import com.example.sample.repository.RestaurantRepository
import com.example.sample.response.EventObserver
import com.example.sample.response.Restaurant
import com.example.sample.viewmodels.RestaurantListViewModelFactory
import com.example.sample.viewmodels.RestaurantViewModel

class RestaurantListFragment : Fragment() {
    private lateinit var binding: FragmentRestaurantListBinding

    private lateinit var restaurantAdapter: RestaurantListAdapter

    private val viewModel: RestaurantViewModel by viewModels {
        RestaurantListViewModelFactory(RestaurantRepository(RestaurantApiClient.apiServices))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_restaurant_list, container, false)
        binding.viewModel = viewModel
        initToolBar()
        initView()
        return binding.root
    }

    private fun initToolBar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = "Discover"
    }

    private fun initView() {
        binding.apply {
            rvRestaurantList.layoutManager = LinearLayoutManager(activity)
            retry.setOnClickListener {
                loading.visibility = View.VISIBLE
                retry.visibility = View.GONE
                errorMessage.text = getString(R.string.wait_for_loading)
                viewModel?.retryLoading()
            }
        }
        setObservers()
    }

    private fun setObservers() {
        binding.rvRestaurantList.apply {
            visibility = View.VISIBLE
            restaurantAdapter = RestaurantListAdapter(viewModel, RestaurantListAdapter.RESTAURANT_ITEM_TYPE)
            adapter = restaurantAdapter
        }

        val itemsSize = viewModel.fetchRestaurants()?.value?.size ?: 0
        if (itemsSize > 0) hideLoading()
        viewModel.fetchRestaurants()?.observe(viewLifecycleOwner, Observer { response ->
            if (response.isNotEmpty()) hideLoading()
            restaurantAdapter.submitListFilterable(response.toMutableList())
        })

        viewModel.getEvents().observe(viewLifecycleOwner, EventObserver {
            when (it) {
                is RestaurantViewModel.ViewEvent.FinishedLoading -> {
                    hideLoading()
                }
                is RestaurantViewModel.ViewEvent.ShowError -> {
                    showError(it.errorMsg)
                }
                is RestaurantViewModel.ViewEvent.NavigateToDetail -> {
                    launchRestaurantDetailFragment(it.data)
                }
            }
        })
    }

    private fun hideLoading() {
        binding.apply {
            errorMessage.visibility = View.GONE
            loading.visibility = View.GONE
            retry.visibility = View.GONE
        }
    }

    private fun showError(message: String) {
        hideLoading()
        binding.errorMessage.apply {
            visibility = View.VISIBLE
            text = message

        }
        binding.retry.visibility = View.VISIBLE
    }

    private fun launchRestaurantDetailFragment(restaurant: Restaurant) {

        val fragmentManager = (context as FragmentActivity).supportFragmentManager
        val detailFragment = fragmentManager.findFragmentByTag(RestaurantDetailsFragment.FRAGMENT_TAG)

        if (detailFragment == null) {
            fragmentManager.commit {
                addToBackStack(this@RestaurantListFragment::class.java.simpleName)
                add(
                        R.id.container,
                        RestaurantDetailsFragment.getInstance(restaurant),
                        RestaurantDetailsFragment.FRAGMENT_TAG
                )
            }
        }
    }

}