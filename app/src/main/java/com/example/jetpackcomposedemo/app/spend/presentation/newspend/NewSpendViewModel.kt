package com.example.jetpackcomposedemo.app.spend.presentation.newspend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposedemo.app.spend.domain.usecases.GetCategoryUseCase
import com.example.jetpackcomposedemo.data.entities.SpendCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewSpendViewModel(private val spendCategoryUseCase: GetCategoryUseCase) : ViewModel() {

    private var _categoryList = MutableLiveData<List<SpendCategory>>()
    val categoryList: LiveData<List<SpendCategory>> get() = _categoryList

    fun getCategoryList() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = spendCategoryUseCase()
            withContext(Dispatchers.Main) {
                _categoryList.postValue(result)
            }
        }
    }
}

class NewSpendViewModelFactory(
    private val spendCategoryUseCase: GetCategoryUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewSpendViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewSpendViewModel(spendCategoryUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}