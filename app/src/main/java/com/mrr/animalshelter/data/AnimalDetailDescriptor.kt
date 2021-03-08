package com.mrr.animalshelter.data

import com.mrr.animalshelter.R

class AnimalDetailDescriptor(private val animal: Animal) {

    fun getAnimalId() = animal.animalId

    fun getAdoptionWebPageUrl(): String = "https://asms.coa.gov.tw/Amlapp/App/AnnounceList.aspx?Id=${animal.animalId}&AcceptNum=${animal.animalSubId}&PageType=Adopt"

    fun getAnimalSexResourceId() = when (animal.animalSex) {
        "M" -> R.string.sex_male
        "F" -> R.string.sex_female
        else -> R.string.sex_unknown
    }

    fun getAnimalBodyTypeResourceId() = when (animal.animalBodyType) {
        "SMALL" -> R.string.body_type_small
        "MEDIUM" -> R.string.body_type_medium
        "BIG" -> R.string.body_type_big
        else -> R.string.body_type_unknown
    }

    fun getAnimalAgeResourceId() = when (animal.animalAge) {
        "ADULT" -> R.string.age_adult
        "CHILD" -> R.string.age_child
        else -> R.string.age_unknown
    }

    fun getAnimalSterilizationResourceId() = when (animal.animalSterilization) {
        "T" -> R.string.sterilization_t
        "F" -> R.string.sterilization_f
        else -> R.string.sterilization_unknown
    }

    fun getAnimalBacterinResourceId() = when (animal.animalBacterin) {
        "T" -> R.string.bacterin_t
        "F" -> R.string.bacterin_f
        else -> R.string.bacterin_unknown
    }

    fun getShelterName() = animal.shelterName.split("(")[0]

    fun getShelterTel() = animal.shelterTel

    fun getShelterAddress() = animal.shelterAddress.split("(")[0]
}