package com.mrr.animalshelter.ktx

import android.app.Service
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
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

fun Context.vibrate(millisecond: Long) {
    (getSystemService(Service.VIBRATOR_SERVICE) as Vibrator).run {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                vibrate(VibrationEffect.createOneShot(millisecond, VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else -> vibrate(millisecond)
        }
    }
}