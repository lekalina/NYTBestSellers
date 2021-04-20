package com.lekalina.nytbestsellers

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lekalina.nytbestsellers.data.Book
import com.lekalina.nytbestsellers.data.Category
import com.lekalina.nytbestsellers.db.AppDatabase
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import kotlin.collections.ArrayList

/**
 * This test class is to test the functionality of the actual database
 * Read, Write, and Delete
 *
 * Run this test when you want to ensure that the database is reading, writing, and deleting as expected
 */
@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private val tag: String = "DatabaseTest.kt"
    private val uuid = UUID.randomUUID().toString() // randomly generated unique identifier

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    /**
     * Test for read, write, delete category table
     */
    @Test
    fun categoriesDao() {
        Log.d(tag , "categoriesDao() test running...")
        runBlocking {
            // create category with unique identifier
            val list: MutableList<Category> = ArrayList()
            val cat = Category(uuid, "name", "display name")
            list.add(cat)
            // add the category to the database
            AppDatabase.db.categoriesDao().insertAll(list)
            // confirm the category exists in the database
            assert(AppDatabase.db.categoriesDao().getCategories().contains(cat))
            // delete the category
            AppDatabase.db.categoriesDao().delete(cat)
            // confirm category removed
            assert(!AppDatabase.db.categoriesDao().getCategories().contains(cat))
        }
        Log.d(tag , "categoriesDao() test complete")
    }

    /**
     * Test for read, write, delete for books table
     */
    @Test
    fun booksDao() {
        Log.d(tag , "booksDao() test running...")
        runBlocking {
            // create a book with a unique title
            val bookTitle = "title_$uuid"
            val list: MutableList<Book> = ArrayList()
            val book = Book(bookTitle, "author", "photo", "description", uuid)
            list.add(book)
            // add the book to the database
            AppDatabase.db.booksDao().insertAll(list)
            val books = AppDatabase.db.categoriesDao().getCategories()
            // confirm that the book exists in the database
            assert(books.contains(book))
            // pull the book we just stored by its unique title
            val bookByTitle = AppDatabase.db.booksDao().getBook(bookTitle)
            // confirm the book that was returned matches the original book
            assert(bookByTitle?.title == book.title)
            assert(bookByTitle?.author.equals(book.author))
            assert(bookByTitle?.category_identifier.equals(book.category_identifier))
            assert(bookByTitle?.description.equals(book.description))
            assert(bookByTitle?.image_url.equals(book.image_url))
            // delete book
            AppDatabase.db.booksDao().delete(book)
            // confirm the book was removed
            assert(AppDatabase.db.booksDao().getBook(book.title) == null)
            assert(!AppDatabase.db.booksDao().getBooks(uuid).contains(book))
        }
        Log.d(tag , "booksDao() test complete")
    }
}