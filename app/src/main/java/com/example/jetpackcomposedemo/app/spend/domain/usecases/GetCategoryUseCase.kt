package com.example.jetpackcomposedemo.app.spend.domain.usecases

import com.example.jetpackcomposedemo.app.spend.domain.repository.SpendCategoryRepository
import com.example.jetpackcomposedemo.data.entities.SpendCategory
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(private val repository: SpendCategoryRepository) {

    suspend operator fun invoke(): List<SpendCategory> {
        return repository.getSpendCategoryList()
    }
}