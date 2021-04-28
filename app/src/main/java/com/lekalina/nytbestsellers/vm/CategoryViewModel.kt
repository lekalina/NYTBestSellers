package com.lekalina.nytbestsellers.vm

import androidx.lifecycle.MutableLiveData
import com.lekalina.nytbestsellers.data.Category
import com.lekalina.nytbestsellers.repos.CategoriesRepo

class CategoryViewModel(private val repo: CategoriesRepo = CategoriesRepo()): BaseViewModel() {

    var categories: MutableLiveData<List<Category>> = MutableLiveData()

    fun getData() {
        showLoading.postValue(true)
        repo.getCategories().observeForever { list ->
            categories.postValue(list)
            showLoading.postValue(false)
        }
    }
}