package com.example.jetpackcomposedemo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetpackcomposedemo.data.dao.SpendCategoryDAO
import com.example.jetpackcomposedemo.data.entities.SpendCategory

@Database(entities = [SpendCategory::class], version = 1)
abstract class SpendDB : RoomDatabase() {
    abstract fun spendCategoryDao(): SpendCategoryDAO
}