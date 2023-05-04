package com.mrr.animalshelter.data.element

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.mrr.animalshelter.R

@Keep
enum class AnimalKind(val id: String, @StringRes val nameResourceId: Int) {
    All("", R.string.kind_all),
    Cat("貓", R.string.kind_cat),
    Dog("狗", R.string.kind_dog),
    Other("其他", R.string.kind_other);
}