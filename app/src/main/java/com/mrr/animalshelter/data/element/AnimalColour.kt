package com.mrr.animalshelter.data.element

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.mrr.animalshelter.R

@Keep
enum class AnimalColour(val id: String, @StringRes val nameResourceId: Int) {
    All("", R.string.colour_all),
    White("白", R.string.colour_white),
    Black("黑", R.string.colour_black),
    Gray("灰", R.string.colour_gray),
    Yellow("黃", R.string.colour_yellow),
    Brown("棕", R.string.colour_brown),
    Cream("米", R.string.colour_cream),
    Multi("花", R.string.colour_multi),
    Coffee("咖啡", R.string.colour_coffee),
    Three("三花", R.string.colour_three),
    Tabby("虎斑", R.string.colour_tabby)
}