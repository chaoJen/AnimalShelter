package com.mrr.animalshelter.data

import com.mrr.animalshelter.data.element.*
import java.io.Serializable

data class AnimalFilter(
    var area: AnimalArea = AnimalArea.All,
    var shelter: AnimalShelter = AnimalShelter.All,
    var kind: AnimalKind = AnimalKind.All,
    var sex: AnimalSex = AnimalSex.All,
    var bodyType: AnimalBodyType = AnimalBodyType.All,
    var colour: AnimalColour = AnimalColour.All,
    var age: AnimalAge = AnimalAge.All,
    var sterilization: AnimalSterilization = AnimalSterilization.All,
    var bacterin: AnimalBacterin = AnimalBacterin.All,
    val status: AnimalStatus = AnimalStatus.Open
) : Serializable