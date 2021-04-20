package com.lekalina.nytbestsellers.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.lekalina.nytbestsellers.api.RestCaller
import com.lekalina.nytbestsellers.data.Book
import com.lekalina.nytbestsellers.db.AppDatabase.Companion.db
import kotlinx.coroutines.Dispatchers


class BooksRepo {

    /**
     * Method to get categories which maintains single source of truth through the database
     * and updates the database with the latest network data
     */
    fun getBooks(categoryId: String): LiveData<List<Book>> = liveData(Dispatchers.IO) {
        val dbBooks = db?.booksDao()?.getBooks(categoryId)
        dbBooks?.let { books -> emit(books) }
        val response = RestCaller().getService().getBooksByCategory(categoryId)
        val books = response.body()
        if(response.isSuccessful && books?.status.equals("OK")) {
            books?.results?.books?.let { netBooks ->
                netBooks.forEach { it.category_identifier = categoryId }
                db?.booksDao()?.insertAll(netBooks)
                db?.booksDao()?.getBooks(categoryId)?.let { books -> emit(books) }
            }
        }
    }

    /**
     * Method to get book by title
     */
    fun getBookByTitle(title: String): LiveData<Book> = liveData(Dispatchers.IO) {
        val dbBook = db?.booksDao()?.getBook(title)
        dbBook?.let { book -> emit(book) }
    }

}
