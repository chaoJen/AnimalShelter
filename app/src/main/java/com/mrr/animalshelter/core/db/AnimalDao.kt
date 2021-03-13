package com.mrr.animalshelter.core.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mrr.animalshelter.data.Animal

@Dao
interface AnimalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(animal: Animal)

    @Query("SELECT * FROM CollectedAnimals WHERE animal_id = :animalId LIMIT 1")
    fun get(animalId: Int): LiveData<Animal>

    @Query("SELECT * FROM CollectedAnimals ORDER BY animal_update DESC, cDate DESC")
    fun getAll(): LiveData<List<Animal>>

    @Query("SELECT animal_id FROM CollectedAnimals")
    fun getAllAnimalIds(): LiveData<List<Int>>

    @Delete
    suspend fun delete(animal: Animal)

    @Query("DELETE FROM CollectedAnimals where animal_id = :animalId")
    suspend fun delete(animalId: Int)
}