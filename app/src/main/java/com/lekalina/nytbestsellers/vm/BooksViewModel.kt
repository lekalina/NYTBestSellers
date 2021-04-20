package com.lekalina.nytbestsellers.vm

import androidx.lifecycle.MutableLiveData
import com.lekalina.nytbestsellers.data.Book
import com.lekalina.nytbestsellers.repos.BooksRepo

class BooksViewModel(private val selectedCategory: String?, private val repo: BooksRepo = BooksRepo()): BaseViewModel() {

    var bookList: MutableLiveData<List<Book>> = MutableLiveData()

    fun getData() {
        selectedCategory?.let {
            repo.getBooks(selectedCategory).observeForever { list ->
                bookList.postValue(list)
            }
        }
    }
}