package com.example.jetpackcomposedemo.app.spend.data.repository

import com.example.jetpackcomposedemo.app.spend.data.source.local.CategoryLocalDataSource
import com.example.jetpackcomposedemo.app.spend.domain.repository.SpendCategoryRepository
import com.example.jetpackcomposedemo.data.entities.SpendCategory
import javax.inject.Inject

class SpendCategoryRepositoryImpl @Inject constructor(
    private val local: CategoryLocalDataSource
) : SpendCategoryRepository {
    override suspend fun getSpendCategoryList(): List<SpendCategory> {
        return local.getSpendCategoryList()
    }
}