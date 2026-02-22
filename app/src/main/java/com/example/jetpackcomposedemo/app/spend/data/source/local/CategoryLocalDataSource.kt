package com.example.jetpackcomposedemo.app.spend.data.source.local

import com.example.jetpackcomposedemo.data.dao.SpendCategoryDAO
import com.example.jetpackcomposedemo.data.entities.SpendCategory
import javax.inject.Inject

class CategoryLocalDataSource @Inject constructor(val dao: SpendCategoryDAO) {
    suspend fun getSpendCategoryList(): List<SpendCategory> {
        return dao.getAll()
    }
}