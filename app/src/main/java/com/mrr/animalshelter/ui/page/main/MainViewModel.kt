package com.mrr.animalshelter.ui.page.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mrr.animalshelter.core.AnimalRepository
import com.mrr.animalshelter.data.Animal

class MainViewModel(private val repository: AnimalRepository) : ViewModel() {

    val animals = MutableLiveData<List<Animal>>()

    fun pullAnimals() {

    }
}