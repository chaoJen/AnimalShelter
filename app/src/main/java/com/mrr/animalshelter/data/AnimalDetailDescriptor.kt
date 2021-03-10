package com.mrr.animalshelter.data

import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import com.mrr.animalshelter.R
import com.mrr.animalshelter.data.element.AnimalArea
import java.text.SimpleDateFormat
import java.util.*

class AnimalDetailDescriptor(private val animal: Animal) {

    companion object {
        private val TAG = AnimalDetailDescriptor::class.java.simpleName
    }

    fun getAnimalId() = animal.animalId

    fun getAnimalSubId() = animal.animalSubId

    fun getAlbumFile() = animal.albumFile

    fun getAnimalColour() = animal.animalColour

    fun getAnimalRemark() = animal.animalRemark

    fun getShelterTel() = animal.shelterTel

    fun getAnimalFoundPlace() = animal.animalFoundPlace

    fun getAnimalArea() = AnimalArea.find(animal.animalAreaPkId)

    fun getAdoptionWebPageUrl(): String = "https://asms.coa.gov.tw/Amlapp/App/AnnounceList.aspx?Id=${animal.animalId}&AcceptNum=${animal.animalSubId}&PageType=Adopt"

    @StringRes
    fun getAnimalSexResourceId() = when (animal.animalSex) {
        "M" -> R.string.sex_male
        "F" -> R.string.sex_female
        else -> R.string.sex_unknown
    }

    @StringRes
    fun getAnimalBodyTypeResourceId() = when (animal.animalBodyType) {
        "SMALL" -> R.string.body_type_small
        "MEDIUM" -> R.string.body_type_medium
        "BIG" -> R.string.body_type_big
        else -> R.string.body_type_unknown
    }

    @StringRes
    fun getAnimalAgeResourceId() = when (animal.animalAge) {
        "ADULT" -> R.string.age_adult
        "CHILD" -> R.string.age_child
        else -> R.string.age_unknown
    }

    @StringRes
    fun getAnimalSterilizationResourceId() = when (animal.animalSterilization) {
        "T" -> R.string.sterilization_t
        "F" -> R.string.sterilization_f
        else -> R.string.sterilization_unknown
    }

    @StringRes
    fun getAnimalBacterinResourceId() = when (animal.animalBacterin) {
        "T" -> R.string.bacterin_t
        "F" -> R.string.bacterin_f
        else -> R.string.bacterin_unknown
    }

    fun getShelterName() = animal.shelterName.split("(")[0]

    fun getShelterAddress() = animal.shelterAddress.split("(")[0]

    fun getDayDiff(context: Context, format: String): String {
        var result = animal.animalUpdate
        try {
            result = SimpleDateFormat(format, Locale.TAIWAN).parse(animal.animalUpdate)?.run {
                val diffDay = ((Date().time - time) / 1000 / 60 / 60 / 24).toInt()
                when {
                    diffDay == 0 -> context.getString(R.string.animal_detail_today)
                    diffDay < 7 -> String.format(context.getString(R.string.animal_detail_diff_date_format_day), diffDay)
                    diffDay in 7..13 -> context.getString(R.string.animal_detail_diff_date_format_week)
                    diffDay < Calendar.getInstance().get(Calendar.DAY_OF_YEAR) -> SimpleDateFormat(context.getString(R.string.animal_detail_diff_date_format_month), Locale.TAIWAN).format(this)
                    else -> SimpleDateFormat(context.getString(R.string.animal_detail_diff_date_format_default), Locale.TAIWAN).format(this)
                }
            } ?: animal.animalUpdate
        } catch (e: Exception) {
            Log.d(TAG, e.message ?: e.toString())
        }
        return result
    }
}