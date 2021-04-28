package com.lekalina.nytbestsellers.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.lekalina.nytbestsellers.api.RestCaller
import com.lekalina.nytbestsellers.data.Book
import com.lekalina.nytbestsellers.db.AppDatabase
import java.lang.Exception


class BooksRepo(private val caller: RestCaller = RestCaller()) {

    /**
     * Method to get categories which maintains single source of truth through the database
     * and updates the database with the latest network data
     */
    fun getBooks(categoryId: String): LiveData<List<Book>> = liveData {
        val db = AppDatabase.getInstance()
        val dbBooks = db.booksDao().getBooks(categoryId)
        dbBooks.let { books ->
            if(books.isNotEmpty()) {
                // only emit if has content, otherwise wait for network response
                emit(books)
            }
        }
        try {
            val response = caller.service.getBooksByCategory(categoryId)
            response.results?.books?.let { netBooks ->
                netBooks.forEach { it.categoryIdentifier = categoryId }
                db.booksDao().insertAll(netBooks)
                db.booksDao().getBooks(categoryId).let { books -> emit(books) }
            }
            if(response.results?.books?.isEmpty() != false && dbBooks.isEmpty()) {
                // emit an empty result if no data
                emit(ArrayList<Book>())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (dbBooks.isEmpty()) {
                // emit an empty result if no data
                emit(ArrayList<Book>())
            }
        }
    }

    /**
     * Method to get book by title
     */
    fun getBookByTitle(title: String): LiveData<Book> = liveData {
        val dbBook = AppDatabase.getInstance().booksDao().getBook(title)
        dbBook?.let { book -> emit(book) }
    }

}
