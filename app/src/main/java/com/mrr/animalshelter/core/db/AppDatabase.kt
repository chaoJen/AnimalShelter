package com.mrr.animalshelter.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mrr.animalshelter.data.Animal

@Database(entities = [Animal::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(AppDatabase::class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        AppDatabase::class.java.simpleName
                    ).build()
                }
            }
            return instance!!
        }
    }

    abstract fun getAnimalDao(): AnimalDao
}