package com.lekalina.nytbestsellers

import com.lekalina.nytbestsellers.api.RestCaller
import com.lekalina.nytbestsellers.data.BooksResponse
import com.lekalina.nytbestsellers.data.Categories
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

/**
 * This test class is to ensure the actual network responses are working
 *
 * Run when you want to test the actual NYT service API and response objects
 */
@ExperimentalCoroutinesApi
class NetworkUnitTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Test
    fun categoriesCall() {
        println("categoriesCall() test running...")
        runBlocking {
            val categories = RestCaller(false).getService().getBookCategories()
            val response: Categories? = categories.body()
            val cats = response?.results
            println("expected = ${response?.count ?: 0}, actual = ${cats?.size ?: 9}")
            Assert.assertEquals(response?.count ?: 0, cats?.size ?: 9)
        }
        println("\n")
    }

    @Test
    fun booksCall() {
        println("booksCall() test running...")
        runBlocking {
            val books = RestCaller(false).getService().getBooksByCategory("science")
            val response: BooksResponse? = books.body()
            val bookCategory = response?.results
            val bookList = bookCategory?.books
            println("expected = ${response?.count ?: 0}, actual = ${bookList?.size ?: 9}")
            Assert.assertEquals(response?.count ?: 0, bookList?.size ?: 9)
            bookList?.map { it.category_identifier = bookCategory.category_identifier }
            println("expected = ${bookCategory?.category_identifier ?: "a"}, actual = ${bookList?.get(0)?.category_identifier ?: "b"}")
            Assert.assertEquals(
                bookCategory?.category_identifier ?: "a",
                bookList?.get(0)?.category_identifier ?: "b"
            )
        }
        println("\n")
    }
}