package com.mrr.animalshelter.data.element

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.mrr.animalshelter.R

@Keep
enum class AnimalStatus(val id: String, @StringRes val nameResourceId: Int) {
    All("", R.string.status_all),
    Open("OPEN", R.string.status_open),
    Adopted("ADOPTED", R.string.status_adopted),
    None("NONE", R.string.status_none),
    Other("OTHER", R.string.status_other),
    Dead("DEAD", R.string.status_dead)
}