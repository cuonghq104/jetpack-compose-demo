package com.example.jetpackcomposedemo

import android.app.Application
import com.example.jetpackcomposedemo.app.spend.data.repository.SpendCategoryRepositoryImpl
import com.example.jetpackcomposedemo.data.db.SpendDB
import com.example.jetpackcomposedemo.di.DaggerApplicationComponent

class MainApplication : Application() {

    val appComponent by lazy {
        DaggerApplicationComponent.builder().applicationContext(this@MainApplication)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this@MainApplication)
    }
}