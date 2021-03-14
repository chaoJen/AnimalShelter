package com.mrr.animalshelter.ktx

import android.app.Service
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.internal.Primitives
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

@Throws(Exception::class)
fun Context.putPreference(preferencesName: String, key: String, value: Any) {
    val sharedPreferences = getSharedPreferences(preferencesName)
    when (value.javaClass) {
        String::class.javaObjectType -> sharedPreferences.edit().putString(key, value as String).apply()
        Boolean::class.javaObjectType -> sharedPreferences.edit().putBoolean(key, value as Boolean).apply()
        Int::class.javaObjectType -> sharedPreferences.edit().putInt(key, value as Int).apply()
        Float::class.javaObjectType -> sharedPreferences.edit().putFloat(key, value as Float).apply()
        Long::class.javaObjectType -> sharedPreferences.edit().putLong(key, value as Long).apply()
        else -> sharedPreferences.edit().putString(key, Gson().toJson(value)).apply()
    }
}

@Throws(Exception::class)
fun <T> Context.getPreference(preferencesName: String, key: String, clazz: Class<T>): T? {
    val sharedPreferences = getSharedPreferences(preferencesName)
    return when (Primitives.wrap(clazz)) {
        String::class.javaObjectType -> clazz.cast(sharedPreferences.getString(key, ""))!!
        Boolean::class.javaObjectType -> clazz.cast(sharedPreferences.getBoolean(key, false))!!
        Int::class.javaObjectType -> clazz.cast(sharedPreferences.getInt(key, 0))!!
        Float::class.javaObjectType -> clazz.cast(sharedPreferences.getFloat(key, 0.0f))!!
        Long::class.javaObjectType -> clazz.cast(sharedPreferences.getLong(key, 0))!!
        Double::class.javaObjectType -> Gson().fromJson(sharedPreferences.getString(key, "0"), clazz)
        else -> Gson().fromJson(sharedPreferences.getString(key, ""), clazz)
    }
}

@Throws(Exception::class)
fun Context.removePreference(preferencesName: String, key: String) {
    val sharedPreferences = getSharedPreferences(preferencesName)
    sharedPreferences.edit().remove(key).apply()
}

@Throws(Exception::class)
private fun Context.getSharedPreferences(preferencesName: String): SharedPreferences = getSharedPreferences(preferencesName, Context.MODE_PRIVATE)