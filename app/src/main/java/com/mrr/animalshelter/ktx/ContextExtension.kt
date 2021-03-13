package com.mrr.animalshelter.ktx

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.mrr.animalshelter.R

fun Context.loadImage(imageUrl: String, imageView: ImageView) {
    Glide.with(this)
        .load(imageUrl)
        .placeholder(R.drawable.ic_placeholder)
        .fitCenter()
        .into(imageView)
}