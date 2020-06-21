package com.example.sample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sample.R
import com.example.sample.adapters.RestaurantListAdapter
import com.example.sample.databinding.FragmentRestaurantListBinding
import com.example.sample.network.RestaurantApiClient
import com.example.sample.repository.RestaurantRepository
import com.example.sample.viewmodels.RestaurantViewModel

class RestaurantListFragment : Fragment() {
    private lateinit var binding: FragmentRestaurantListBinding

    private lateinit var restaurantAdapter: RestaurantListAdapter

    private lateinit var viewModel: RestaurantViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_restaurant_list, container, false)
        viewModel = ViewModelProviders.of(this).get(RestaurantViewModel::class.java)
        viewModel.init(repo = RestaurantRepository(RestaurantApiClient.apiServices))
        binding.viewModel = viewModel
        initView()
        return binding.root
    }

    private fun initView() {
        binding.apply {
            rvRestaurantList.layoutManager = LinearLayoutManager(activity)
            btRetry.setOnClickListener {
                pbLoading.visibility = View.VISIBLE
                btRetry.visibility = View.GONE
                tvScreenMsg.text = getString(R.string.wait_for_loading)
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

        val itemsSize = viewModel.restaurantsList?.value?.size ?: 0
        if (itemsSize > 0) hideLoading()
        viewModel.restaurantsList?.observe(viewLifecycleOwner, Observer { response ->
            if (response.isNotEmpty()) hideLoading()
            restaurantAdapter.submitListFilterable(response.toMutableList())
        })
    }

    private fun hideLoading() {
        binding.apply {
            tvScreenMsg.visibility = View.GONE
            pbLoading.visibility = View.GONE
            btRetry.visibility = View.GONE
        }
    }

    private fun showError(message: String) {
        hideLoading()
        binding.tvScreenMsg.apply {
            visibility = View.VISIBLE
            text = message

        }
        binding.btRetry.visibility = View.VISIBLE
    }
}