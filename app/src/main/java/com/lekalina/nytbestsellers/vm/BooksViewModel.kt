package com.lekalina.nytbestsellers.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lekalina.nytbestsellers.data.Book
import com.lekalina.nytbestsellers.repos.BooksRepo

class BooksViewModel(private val selectedCategory: String?, private val repo: BooksRepo = BooksRepo()): BaseViewModel() {

    var bookList: MutableLiveData<List<Book>> = MutableLiveData()

    fun getData() {
        showLoading.postValue(true)
        selectedCategory?.let {
            repo.getBooks(selectedCategory).observeForever { list ->
                bookList.postValue(list)
                showLoading.postValue(false)
            }
        }
    }
}

/**
 * Custom view model provider factory method to pass in a parameter
 */
class BooksViewModelFactory(private val selectedCategory: String?): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BooksViewModel::class.java)) {
            return BooksViewModel(selectedCategory) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}