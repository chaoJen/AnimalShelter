package com.mrr.animalshelter.core

import com.mrr.animalshelter.core.api.service.ShelterService
import com.mrr.animalshelter.core.db.AnimalDao
import com.mrr.animalshelter.data.Animal
import com.mrr.animalshelter.data.AnimalFilter
import com.mrr.animalshelter.data.element.AnimalArea
import com.mrr.animalshelter.data.element.AnimalShelter
import retrofit2.Response

class AnimalRepository(
    private val service: ShelterService,
    private val storedAnimalsDao: AnimalDao
) {

    @Throws(Throwable::class)
    suspend fun fetchAnimal(animalId: Int) = service.getAnimal(animalId)

    @Throws(Throwable::class)
    suspend fun fetchAnimals(top: Int, skip: Int, filter: AnimalFilter): Response<List<Animal>> {
        return service.getAnimals(
            top = top,
            skip = skip,
            animalAreaPkId = if (filter.area != AnimalArea.All) "${filter.area.id}" else "",
            animalShelterPkId = if (filter.shelter != AnimalShelter.All) "${filter.shelter.id}" else "",
            animalKind = filter.kind.id,
            animalSex = filter.sex.id,
            animalBodyType = filter.bodyType.id,
            animalColour = filter.colour.id,
            animalAge = filter.age.id,
            animalSterilization = filter.sterilization.id,
            animalBacterin = filter.bacterin.id,
            animalStatus = filter.status.id
        )
    }

    suspend fun storeAnimal(animal: Animal) {
        storedAnimalsDao.insert(animal)
    }

    suspend fun getAllStoredAnimals() = storedAnimalsDao.getAll()

    fun getAllStoredAnimalsAsLiveData() = storedAnimalsDao.getAllAsLiveData()

    fun getAllStoredAnimalIds() = storedAnimalsDao.getAllAnimalIds()

    suspend fun unStoreAnimal(animalId: Int) = storedAnimalsDao.delete(animalId)
}