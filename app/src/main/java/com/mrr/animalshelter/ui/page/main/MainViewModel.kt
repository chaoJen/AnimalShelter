package com.mrr.animalshelter.ui.page.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrr.animalshelter.core.AnimalRepository
import com.mrr.animalshelter.core.const.ShelterServiceConst
import com.mrr.animalshelter.data.Animal
import com.mrr.animalshelter.data.AnimalFilter
import com.mrr.animalshelter.data.element.ErrorType
import com.mrr.animalshelter.ui.base.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AnimalRepository) : ViewModel() {

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    val animals = MutableLiveData<List<Animal>>()
    val collectedAnimals = repository.getAllCollectedAnimals()
    val collectedAnimalIds = repository.getAllCollectedAnimalIds()
    val isAnimalLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<ErrorType>()
    val isNoMoreData = MutableLiveData<Boolean>()

    val onScrollGalleryToPositionEvent = SingleLiveEvent<Int>()
    val onScrollCollectionGalleryToPositionEvent = SingleLiveEvent<Int>()
    val onLaunchAnimalDetailToPositionEvent = SingleLiveEvent<Int>()
    val onLaunchCollectionAnimalDetailToPositionEvent = SingleLiveEvent<Int>()

    private var mSkip = 0
    private val mTop = ShelterServiceConst.TOP

    fun pullAnimals(filter: AnimalFilter) = viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
        Log.d(TAG, throwable.toString())
        isAnimalLoading.postValue(false)
        error.postValue(ErrorType.ApiException(throwable))
    }) {
        if (isAnimalLoading.value == true || isNoMoreData.value == true) {
            return@launch
        }
        isAnimalLoading.postValue(true)
        val response = repository.pullAnimals(mTop, mSkip, filter)
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
        isAnimalLoading.postValue(false)
    }

    fun resetAnimals(filter: AnimalFilter) {
        mSkip = 0
        isNoMoreData.postValue(false)
        pullAnimals(filter)
    }

    fun collectAnimal(animal: Animal) = viewModelScope.launch {
        repository.collectAnimal(animal)
    }

    fun unCollectAnimal(animalId: Int) = viewModelScope.launch {
        repository.unCollectAnimal(animalId)
    }

    fun scrollGallery(position: Int) {
        onScrollGalleryToPositionEvent.postValue(position)
    }

    fun scrollCollectionGallery(position: Int) {
        onScrollCollectionGalleryToPositionEvent.postValue(position)
    }

    fun launchGalleryAnimalDetail(position: Int) {
        onLaunchAnimalDetailToPositionEvent.postValue(position)
    }

    fun launchCollectionAnimalDetail(position: Int) {
        onLaunchCollectionAnimalDetailToPositionEvent.postValue(position)
    }
}