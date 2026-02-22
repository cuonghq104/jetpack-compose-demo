package com.example.jetpackcomposedemo

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import com.example.jetpackcomposedemo.di.ActivityComponent
import com.example.jetpackcomposedemo.di.DaggerActivityComponent

open class BaseActivity : ComponentActivity() {
    lateinit var daggerActivityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        daggerActivityComponent = DaggerActivityComponent.builder()
            .activity(this)
            .build()

        daggerActivityComponent.inject(this)
    }
}