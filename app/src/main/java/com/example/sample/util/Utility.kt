package com.example.sample.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.sample.R

@BindingAdapter("loadUrl", requireAll = true)
fun ImageView.loadUrl(imagePath: String?) {

    if (imagePath == null) return
    Glide.with(this)
        .load(imagePath)
        .centerCrop()
        .placeholder(R.drawable.ic_placeholder)
        .into(this)

}