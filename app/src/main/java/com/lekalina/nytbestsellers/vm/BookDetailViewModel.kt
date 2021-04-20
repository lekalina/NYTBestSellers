package com.lekalina.nytbestsellers.vm

import androidx.lifecycle.MutableLiveData
import com.lekalina.nytbestsellers.data.Book
import com.lekalina.nytbestsellers.repos.BooksRepo

class BookDetailViewModel(title: String?, repo: BooksRepo = BooksRepo()): BaseViewModel() {

    val book: MutableLiveData<Book> = MutableLiveData()

    init {
        title?.let {
            repo.getBookByTitle(title).observeForever {
                book.postValue(it)
            }
        }
    }
}