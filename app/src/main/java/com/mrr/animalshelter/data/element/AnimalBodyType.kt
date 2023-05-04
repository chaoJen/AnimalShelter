package com.mrr.animalshelter.data.element

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.mrr.animalshelter.R

@Keep
enum class AnimalBodyType(val id: String, @StringRes val nameResourceId: Int) {
    All("", R.string.body_type_all),
    Small("SMALL", R.string.body_type_small),
    Medium("MEDIUM", R.string.body_type_medium),
    Big("BIG", R.string.body_type_big)
}