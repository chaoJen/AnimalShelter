package com.mrr.animalshelter.ui.page.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrr.animalshelter.core.AnimalRepository
import com.mrr.animalshelter.core.const.ShelterServiceConst
import com.mrr.animalshelter.data.Animal
import com.mrr.animalshelter.data.AnimalFilter
import com.mrr.animalshelter.data.element.AnimalArea
import com.mrr.animalshelter.data.element.AnimalShelter
import com.mrr.animalshelter.data.element.ErrorType
import com.mrr.animalshelter.ui.base.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AnimalRepository, animalFilter: AnimalFilter) : ViewModel() {

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    val animals = MutableLiveData<List<Animal>>()
    val animalFilter = MutableLiveData<AnimalFilter>(animalFilter)
    val collectionAnimals = repository.getAllCollectionAnimalsAsLiveData()
    val collectionAnimalIds = repository.getAllCollectionAnimalIds()
    val isGalleryDataPulling = MutableLiveData<Boolean>()
    val isCollectionDataPulling = MutableLiveData<Boolean>()
    val error = MutableLiveData<ErrorType>()
    val isNoMoreData = MutableLiveData<Boolean>()

    val onScrollGalleryToPositionEvent = SingleLiveEvent<Int>()
    val onScrollCollectionGalleryToPositionEvent = SingleLiveEvent<Int>()
    val onLaunchGalleryAnimalDetailToPositionEvent = SingleLiveEvent<Int>()
    val onLaunchCollectionAnimalDetailToPositionEvent = SingleLiveEvent<Int>()
    val onBackCollectionAnimalDetailEvent = SingleLiveEvent<Unit>()
    val onShowFilterBottomSheetShelterEvent = SingleLiveEvent<List<AnimalShelter>>()

    private var mSkip = 0
    private val mTop = ShelterServiceConst.TOP

    fun pullAnimals() = viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
        Log.d(TAG, throwable.toString())
        isGalleryDataPulling.postValue(false)
        error.postValue(ErrorType.ApiException(throwable))
    }) {
        if (isGalleryDataPulling.value == true || isNoMoreData.value == true) {
            return@launch
        }
        isGalleryDataPulling.postValue(true)
        val response = repository.pullAnimals(mTop, mSkip, animalFilter.value ?: AnimalFilter())
        if (response.isSuccessful) {
            val newAnimals = response.body()
            when {
                newAnimals == null -> error.postValue(ErrorType.ApiFail)
                newAnimals.isEmpty() -> isNoMoreData.postValue(true)
                else -> {
                    val newFilteredAnimals = newAnimals.filter { animal -> animal.albumFile.isNotBlank() }
                    val currentAnimals = if (mSkip != 0) animals.value?.toMutableList() ?: mutableListOf() else mutableListOf()
                    animals.postValue(currentAnimals.also { it.addAll(newFilteredAnimals) })
                    mSkip += mTop
                }
            }
        } else {
            error.postValue(ErrorType.ApiFail)
        }
        isGalleryDataPulling.postValue(false)
    }

    fun resetAnimals() {
        mSkip = 0
        isNoMoreData.value = false
        pullAnimals()
    }

    fun collectAnimal(animal: Animal) = viewModelScope.launch {
        repository.collectAnimal(animal)
    }

    fun unCollectAnimal(animalId: Int) = viewModelScope.launch {
        repository.unCollectAnimal(animalId)
    }

    fun changeAnimalCollection(animal: Animal) {
        if (collectionAnimals.value?.contains(animal) == true) {
            unCollectAnimal(animal.animalId)
        } else {
            collectAnimal(animal)
        }
    }

    fun updateCollectionAnimalsData() = viewModelScope.launch {
        isCollectionDataPulling.postValue(true)
        val collectionAnimals = repository.getAllCollectionAnimals()
        collectionAnimals.forEach { animal ->
            val response = repository.pullAnimal(animal.animalId)
            if (response.isSuccessful) {
                val animals = response.body()
                if (animals?.size == 1) {
                    collectAnimal(animals[0])
                }
            }
        }
        isCollectionDataPulling.postValue(false)
    }

    fun scrollGallery(position: Int) {
        onScrollGalleryToPositionEvent.postValue(position)
    }

    fun scrollCollection(position: Int) {
        onScrollCollectionGalleryToPositionEvent.postValue(position)
    }

    fun launchGalleryAnimalDetail(animal: Animal) {
        onLaunchGalleryAnimalDetailToPositionEvent.postValue(animals.value?.indexOf(animal) ?: 0)
    }

    fun launchCollectionAnimalDetail(animal: Animal) {
        onLaunchCollectionAnimalDetailToPositionEvent.postValue(collectionAnimals.value?.indexOf(animal) ?: 0)
    }

    fun backCollectionAnimalDetail() {
        onBackCollectionAnimalDetailEvent.postValue(Unit)
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
            animalFilter.postValue(animalFilter.value?.also {
                it.shelter = shelter
                if (shelter.area != AnimalArea.All) {
                    it.area = shelter.area
                }
            })
            resetAnimals()
        }
    }

    fun showFilterBottomSheetShelter() {
        val filterArea = animalFilter.value?.area ?: AnimalArea.All
        onShowFilterBottomSheetShelterEvent.postValue(AnimalShelter.find(filterArea))
    }
}