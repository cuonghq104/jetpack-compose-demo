package com.example.jetpackcomposedemo.app.spend.data.source.local

import com.example.jetpackcomposedemo.data.dao.SpendCategoryDAO
import com.example.jetpackcomposedemo.data.entities.SpendCategory

class CategoryLocalDataSource(val dao: SpendCategoryDAO) {
    suspend fun getSpendCategoryList(): List<SpendCategory> {
        return dao.getAll()
    }
}