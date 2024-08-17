package com.example.filmes_atores_project

import android.app.Application
import com.example.filmes_atores_project.data.AppDataBase

class MovieApplication: Application() {
    val database: AppDataBase by lazy{
        AppDataBase.getDataBase(this)
    }
}