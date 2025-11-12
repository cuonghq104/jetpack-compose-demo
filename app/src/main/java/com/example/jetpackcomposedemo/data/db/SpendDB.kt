package com.example.jetpackcomposedemo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jetpackcomposedemo.data.dao.SpendCategoryDAO
import com.example.jetpackcomposedemo.data.entities.SpendCategory

@Database(entities = [SpendCategory::class], version = 1)
abstract class SpendDB : RoomDatabase() {
    abstract fun spendCategoryDao(): SpendCategoryDAO

    companion object {
        private lateinit var instance: SpendDB
        fun getInstance(context: Context): SpendDB {
            if (!::instance.isInitialized) {
                instance = Room.databaseBuilder(context, SpendDB::class.java, "main.db")
                    .createFromAsset("database/app.db")
                    .build()
            }
            return instance
        }
    }
}