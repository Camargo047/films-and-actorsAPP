package com.example.filmes_atores_project.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Movie::class, Actor::class, MovieActor::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object{
        @Volatile
        private var INSTANCE: AppDataBase? = null
        fun getDataBase(context: Context): AppDataBase{
            return INSTANCE?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database"
                )

                    .fallbackToDestructiveMigration()
                    .build()
                    .also{
                        INSTANCE = it
                    }
            }
        }
    }
}