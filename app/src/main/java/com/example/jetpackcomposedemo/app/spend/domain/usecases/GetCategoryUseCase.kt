package com.example.jetpackcomposedemo.app.spend.domain.usecases

import com.example.jetpackcomposedemo.app.spend.domain.repository.SpendCategoryRepository
import com.example.jetpackcomposedemo.data.entities.SpendCategory

class GetCategoryUseCase(private val repository: SpendCategoryRepository) {

    suspend operator fun invoke(): List<SpendCategory> {
        return repository.getSpendCategoryList()
    }
}