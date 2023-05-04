package com.mrr.animalshelter.data.element

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.mrr.animalshelter.R

@Keep
enum class AnimalAge(val id: String, @StringRes val nameResourceId: Int) {
    All("", R.string.age_all),
    Child("CHILD", R.string.age_child),
    Adult("ADULT", R.string.age_adult)
}