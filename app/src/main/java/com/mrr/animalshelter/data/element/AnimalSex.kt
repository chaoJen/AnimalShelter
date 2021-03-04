package com.mrr.animalshelter.data.element

import androidx.annotation.StringRes
import com.mrr.animalshelter.R

enum class AnimalSex(val id: String, @StringRes val nameResourceId: Int) {
    All("", R.string.sex_all),
    Male("M", R.string.sex_male),
    Female("F", R.string.sex_female)
//    Unknown("N", R.string.sex_unknown)
}