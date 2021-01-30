package com.mrr.animalshelter.core.api.service

import com.mrr.animalshelter.data.Animal
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ShelterService {
    @GET("Service/OpenData/TransService.aspx?UnitId=QcbUEzN6E6DL")
    suspend fun getAnimalList(
        @Query("\$top") top: Int = 1000,
        @Query("\$skip") skip: Int = 0,
        @Query("animal_area_pkid") animalAreaPkId: String = "",
        @Query("animal_shelter_pkid") animalShelterPkId: String = "",
        @Query("animal_kind") animalKind: String = "",
        @Query("animal_sex") animalSex: String = "",
        @Query("animal_bodytype") animalBodyType: String = "",
        @Query("animal_colour") animalColour: String = "",
        @Query("animal_age") animalAge: String = "",
        @Query("animal_sterilization") animalSterilization: String = "",
        @Query("animal_bacterin") animalBacterin: String = "",
        @Query("animal_status") animalStatus: String = ""
    ): Response<List<Animal>>
}