package com.example.jetpackcomposedemo.app.spend.domain.repository

import com.example.jetpackcomposedemo.data.entities.SpendCategory

interface SpendCategoryRepository {
    suspend fun getSpendCategoryList(): List<SpendCategory>
}