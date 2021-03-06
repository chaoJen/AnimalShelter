package com.mrr.animalshelter.core

import com.mrr.animalshelter.core.api.service.ShelterService
import com.mrr.animalshelter.data.Animal
import com.mrr.animalshelter.data.AnimalFilter
import com.mrr.animalshelter.data.element.*
import retrofit2.Response

class AnimalRepository(private val service: ShelterService) {

    suspend fun pullAnimals(top: Int, skip: Int, filter: AnimalFilter): Response<List<Animal>> {
        return service.getAnimals(
            top = top,
            skip = skip,
            animalAreaPkId = "${if (filter.area != AnimalArea.All) filter.area?.id ?: AnimalArea.All.id else ""}",
            animalShelterPkId = "${if (filter.shelter != AnimalShelter.All) filter.shelter?.id ?: AnimalShelter.All.id else ""}",
            animalKind = filter.kind?.id ?: AnimalKind.All.id,
            animalSex = filter.sex?.id ?: AnimalSex.All.id,
            animalBodyType = filter.bodyType?.id ?: AnimalBodyType.All.id,
            animalColour = filter.colour?.id ?: AnimalColour.All.id,
            animalAge = filter.age?.id ?: AnimalAge.All.id,
            animalSterilization = filter.sterilization?.id ?: AnimalSterilization.All.id,
            animalBacterin = filter.bacterin?.id ?: AnimalBacterin.All.id,
            animalStatus = filter.status?.id ?: AnimalStatus.Open.id
        )
    }
}