package com.example.jetpackcomposedemo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.jetpackcomposedemo.data.entities.SpendCategory

@Dao
interface SpendCategoryDAO {
    @Query("SELECT * FROM tbl_spend_category ORDER BY type")
    fun getAll(): List<SpendCategory>

    @Insert
    fun insert(category: SpendCategory)

    @Insert
    fun insertAll(vararg categories: SpendCategory)
}