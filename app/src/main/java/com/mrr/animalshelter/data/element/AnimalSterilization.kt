package com.mrr.animalshelter.data.element

import androidx.annotation.StringRes
import com.mrr.animalshelter.R

enum class AnimalSterilization(val id: String, @StringRes val nameResourceId: Int) {
    All("", R.string.sterilization_all),
    Yes("T", R.string.sterilization_t),
    No("F", R.string.sterilization_f)
}