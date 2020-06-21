package com.example.sample.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.databinding.ItemViewBinding
import com.example.sample.response.Restaurant
import com.example.sample.viewmodels.RestaurantItemViewModel
import com.example.sample.viewmodels.RestaurantViewModel

class RestaurantListAdapter(
        private val viewModel: RestaurantViewModel?,
        private val itemType: Int) : ListAdapter<Restaurant, RecyclerView.ViewHolder>(RestaurantCallBack()){

    companion object {
        const val RESTAURANT_ITEM_TYPE = 1001
    }

    class RestaurantCallBack : DiffUtil.ItemCallback<Restaurant>() {
        override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            MovieVH(DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_view, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieVH) {
            viewModel?.let {
                holder.binding.viewModel = RestaurantItemViewModel(
                        getItem(position), viewModel
                )
            }
        }
    }

    override fun getItemViewType(position: Int) = itemType

    fun submitListFilterable(list: MutableList<Restaurant>?) {
        submitList(list)
    }

}

class MovieVH(val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root)

