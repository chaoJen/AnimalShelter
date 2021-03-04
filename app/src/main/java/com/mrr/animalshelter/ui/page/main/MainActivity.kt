package com.mrr.animalshelter.ui.page.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mrr.animalshelter.R
import com.mrr.animalshelter.core.AnimalRepository
import com.mrr.animalshelter.core.api.ApiManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = ApiManager.getShelterService()
        val repository = AnimalRepository(service)
        val viewModel = MainViewModel(repository)
    }
}