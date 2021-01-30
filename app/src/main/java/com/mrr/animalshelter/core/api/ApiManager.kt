package com.mrr.animalshelter.core.api

import com.mrr.animalshelter.BuildConfig
import com.mrr.animalshelter.core.api.service.ShelterService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {

    companion object {

        @Volatile
        private var mShelterService: ShelterService? = null

        fun getShelterService(): ShelterService {
            return mShelterService ?: synchronized(this) {
                mShelterService ?: createShelterService().also { mShelterService = it }
            }
        }

        private fun createShelterService(): ShelterService {
            val loggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.GOVERNMENT_OPEN_DATA_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(ShelterService::class.java)
        }
    }
}