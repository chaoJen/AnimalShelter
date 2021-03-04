package com.mrr.animalshelter.data.element

import androidx.annotation.StringRes
import com.mrr.animalshelter.R

enum class AnimalBacterin(val id: String, @StringRes val nameResourceId: Int) {
    All("", R.string.bacterin_all),
    Yes("T", R.string.bacterin_t),
    No("F", R.string.bacterin_f)
//    Unknown("N", R.string.bacterin_unknown)
}