package com.mrr.animalshelter.ui.page.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrr.animalshelter.core.AnimalRepository
import com.mrr.animalshelter.core.const.ShelterServiceConst
import com.mrr.animalshelter.data.Animal
import com.mrr.animalshelter.data.AnimalFilter
import com.mrr.animalshelter.data.element.*
import com.mrr.animalshelter.data.exception.HttpException
import com.mrr.animalshelter.data.exception.ResponseException
import com.mrr.animalshelter.core.base.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AnimalRepository, animalFilter: AnimalFilter) : ViewModel() {

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    val animals = MutableLiveData<List<Animal>>()
    val animalFilter = MutableLiveData<AnimalFilter>(animalFilter)
    val collectedAnimals = repository.getAllStoredAnimalsAsLiveData()
    val collectedAnimalIds = repository.getAllStoredAnimalIds()
    val isSearchDataPulling = MutableLiveData<Boolean>()
    val isCollectedDataPulling = MutableLiveData<Boolean>()
    val exception = MutableLiveData<Throwable>()
    val isNoMoreData = MutableLiveData<Boolean>()

    val onScrollAnimalShelterSearchToPositionEvent = SingleLiveEvent<Int>()
    val onScrollAnimalShelterCollectedToPositionEvent = SingleLiveEvent<Int>()
    val onLaunchAnimalShelterSearchDetailToPositionEvent = SingleLiveEvent<Int>()
    val onLaunchCollectionAnimalDetailToPositionEvent = SingleLiveEvent<Int>()
    val onLaunchSearchFilterEvent = SingleLiveEvent<Unit>()
    val onShowFilterBottomSheetShelterEvent = SingleLiveEvent<List<AnimalShelter>>()

    private var mSkip = 0
    private val mTop = ShelterServiceConst.TOP

    fun pullAnimals() = viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
        Log.d(TAG, throwable.toString())
        isSearchDataPulling.postValue(false)
        exception.postValue(throwable)
    }) {
        if (isSearchDataPulling.value == true || isNoMoreData.value == true) {
            return@launch
        }
        isSearchDataPulling.postValue(true)
        val response = try {
            repository.fetchAnimals(mTop, mSkip, animalFilter.value ?: AnimalFilter())
        } catch (t: Throwable) {
            throw HttpException(t)
        }
        if (response.isSuccessful) {
            val newAnimals = response.body()
            when {
                newAnimals == null -> exception.postValue(ResponseException())
                newAnimals.isEmpty() && mSkip != 0 -> isNoMoreData.postValue(true)
                else -> {
                    val newFilteredAnimals = newAnimals.filter { animal -> animal.albumFile.isNotBlank() }
                    val currentAnimals = if (mSkip != 0) animals.value?.toMutableList() ?: mutableListOf() else mutableListOf()
                    animals.postValue(currentAnimals.also { it.addAll(newFilteredAnimals) })
                    mSkip += mTop
                }
            }
        } else {
            exception.postValue(ResponseException())
        }
        isSearchDataPulling.postValue(false)
    }

    fun resetAnimals() {
        mSkip = 0
        isNoMoreData.value = false
        pullAnimals()
    }

    fun collectAnimal(animal: Animal) = viewModelScope.launch {
        repository.storeAnimal(animal)
    }

    fun unCollectAnimal(animalId: Int) = viewModelScope.launch {
        repository.unStoreAnimal(animalId)
    }

    fun changeAnimalCollection(animal: Animal) {
        if (collectedAnimals.value?.contains(animal) == true) {
            unCollectAnimal(animal.animalId)
        } else {
            collectAnimal(animal)
        }
    }

    fun updateCollectionAnimalsData() = viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.d(TAG, throwable.toString())
        isCollectedDataPulling.postValue(false)
        exception.postValue(throwable)
    }) {
        isCollectedDataPulling.postValue(true)
        val collectionAnimals = repository.getAllStoredAnimals()
        collectionAnimals.forEach { animal ->
            val response = try {
                repository.fetchAnimal(animal.animalId)
            } catch (t: Throwable) {
                throw HttpException(t)
            }
            if (response.isSuccessful) {
                val animals = response.body()
                if (animals?.size == 1) {
                    collectAnimal(animals[0])
                }
            }
        }
        isCollectedDataPulling.postValue(false)
    }

    fun scrollAnimalShelterSearchMainTo(position: Int) {
        onScrollAnimalShelterSearchToPositionEvent.postValue(position)
    }

    fun scrollAnimalShelterCollectedMainTo(position: Int) {
        onScrollAnimalShelterCollectedToPositionEvent.postValue(position)
    }

    fun launchAnimalShelterSearchDetail(animal: Animal) {
        onLaunchAnimalShelterSearchDetailToPositionEvent.postValue(animals.value?.indexOf(animal) ?: 0)
    }

    fun launchAnimalShelterCollectedDetail(animal: Animal) {
        onLaunchCollectionAnimalDetailToPositionEvent.postValue(collectedAnimals.value?.indexOf(animal) ?: 0)
    }

    fun launchSearchFilter() {
        onLaunchSearchFilterEvent.value = Unit
    }

    fun filterArea(area: AnimalArea) {
        if (animalFilter.value?.area != area) {
            animalFilter.postValue(animalFilter.value?.also {
                it.area = area
                val areaShelters = AnimalShelter.find(area)
                if (areaShelters.size == 1 && animalFilter.value?.shelter != areaShelters[0]) {
                    it.shelter = areaShelters[0]
                } else if (animalFilter.value?.shelter?.area != area) {
                    it.shelter = AnimalShelter.All
                }
            })
            resetAnimals()
        }
    }

    fun filterShelter(shelter: AnimalShelter) {
        if (animalFilter.value?.shelter != shelter) {
            animalFilter.postValue(animalFilter.value?.also { it.shelter = shelter })
            resetAnimals()
        }
    }

    fun filterKind(kind: AnimalKind) {
        if (animalFilter.value?.kind != kind) {
            animalFilter.postValue(animalFilter.value?.also { it.kind = kind })
            resetAnimals()
        }
    }

    fun filterSex(sex: AnimalSex) {
        if (animalFilter.value?.sex != sex) {
            animalFilter.postValue(animalFilter.value?.also { it.sex = sex })
            resetAnimals()
        }
    }

    fun filterAge(age: AnimalAge) {
        if (animalFilter.value?.age != age) {
            animalFilter.postValue(animalFilter.value?.also { it.age = age })
            resetAnimals()
        }
    }

    fun filterBodyType(bodyType: AnimalBodyType) {
        if (animalFilter.value?.bodyType != bodyType) {
            animalFilter.postValue(animalFilter.value?.also { it.bodyType = bodyType })
            resetAnimals()
        }
    }

    fun filterColour(colour: AnimalColour) {
        if (animalFilter.value?.colour != colour) {
            animalFilter.postValue(animalFilter.value?.also { it.colour = colour })
            resetAnimals()
        }
    }

    fun filterBacterin(bacterin: AnimalBacterin) {
        if (animalFilter.value?.bacterin != bacterin) {
            animalFilter.postValue(animalFilter.value?.also { it.bacterin = bacterin })
            resetAnimals()
        }
    }

    fun filterSterilization(sterilization: AnimalSterilization) {
        if (animalFilter.value?.sterilization != sterilization) {
            animalFilter.postValue(animalFilter.value?.also { it.sterilization = sterilization })
            resetAnimals()
        }
    }

    fun resetFilter() {
        if (animalFilter.value != AnimalFilter()) {
            animalFilter.value = AnimalFilter()
            resetAnimals()
        }
    }

    fun showFilterBottomSheetShelter() {
        val filterArea = animalFilter.value?.area ?: AnimalArea.All
        onShowFilterBottomSheetShelterEvent.postValue(AnimalShelter.find(filterArea))
    }
}