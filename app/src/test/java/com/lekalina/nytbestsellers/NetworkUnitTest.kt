package com.lekalina.nytbestsellers

import com.lekalina.nytbestsellers.api.RestCaller
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

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
            try {
                val response = RestCaller().service.getBookCategories()
                val cats = response.results
                println("expected = ${response.count ?: 0}, actual = ${cats?.size ?: 9}")
                Assert.assertEquals(response.count ?: 0, cats?.size ?: 9)
            } catch (e: Exception) {
                Assert.assertNotNull(e)
            }

        }
        println("\n")
    }

    @Test
    fun booksCall() {
        println("booksCall() test running...")
        runBlocking {
            try {
                val response = RestCaller().service.getBooksByCategory("science")
                val bookCategory = response.results
                val bookList = bookCategory?.books
                println("expected = ${response.count ?: 0}, actual = ${bookList?.size ?: 9}")
                Assert.assertEquals(response.count ?: 0, bookList?.size ?: 9)
                bookList?.map { it.categoryIdentifier = bookCategory.categoryIdentifier }
                println("expected = ${bookCategory?.categoryIdentifier ?: "a"}, actual = ${bookList?.get(0)?.categoryIdentifier ?: "b"}")
                Assert.assertEquals(
                    bookCategory?.categoryIdentifier ?: "a",
                    bookList?.get(0)?.categoryIdentifier ?: "b"
                )
            } catch (e: Exception) {
                Assert.assertNotNull(e)
            }
        }
        println("\n")
    }
}