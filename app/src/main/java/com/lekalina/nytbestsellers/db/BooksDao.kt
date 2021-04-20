package com.lekalina.nytbestsellers.db

import androidx.room.*
import com.lekalina.nytbestsellers.data.Book

@Dao
interface BooksDao {

    @Query("SELECT * FROM books WHERE category_id LIKE :category ORDER BY title ASC")
    suspend fun getBooks(category: String): List<Book>

    @Query("SELECT * FROM books WHERE title = :title")
    suspend fun getBook(title: String): Book?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(books: List<Book>)

    @Delete
    suspend fun delete(book: Book)
}