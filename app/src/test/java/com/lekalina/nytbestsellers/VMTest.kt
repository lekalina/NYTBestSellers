package com.lekalina.nytbestsellers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.lekalina.nytbestsellers.data.Book
import com.lekalina.nytbestsellers.data.Category
import com.lekalina.nytbestsellers.repos.BooksRepo
import com.lekalina.nytbestsellers.repos.CategoriesRepo
import com.lekalina.nytbestsellers.vm.BaseViewModel
import com.lekalina.nytbestsellers.vm.BookDetailViewModel
import com.lekalina.nytbestsellers.vm.BooksViewModel
import com.lekalina.nytbestsellers.vm.CategoryViewModel
import junit.framework.TestCase.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

/**
 * This test class is for the ViewModels
 * Mock response data is used to test basic ViewModel logic
 */
@RunWith(MockitoJUnitRunner::class)
class VMTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private var categoryList: MutableLiveData<List<Category>> = MutableLiveData()
    private var category = Category("uuid", "name", "display_name")
    private var bookList: MutableLiveData<List<Book>> = MutableLiveData()
    private var book = Book("title", "author", "photo", "description", "uuid")

    class MockClass: BaseViewModel()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val c: MutableList<Category> = ArrayList()
        c.add(category)
        categoryList.postValue(c)
        assertNotNull(categoryList.value)

        val b: MutableList<Book> = ArrayList()
        b.add(book)
        bookList.postValue(b)
        assertNotNull(bookList.value)
    }

    @Test
    fun bookDetailVM() {
        println( "bookDetailVM() test running...")
        // setup mock repo
        val title = "title"
        val data: MutableLiveData<Book> = MutableLiveData()
        data.postValue(book)
        val mockBooksRepo = mock(BooksRepo::class.java)
        Mockito.`when`(mockBooksRepo.getBookByTitle(title)).thenReturn(data)
        val b = mockBooksRepo.getBookByTitle(title).value
        assertTrue(b?.title.equals(book.title))
        assertTrue(b?.author.equals(book.author))
        assertTrue(b?.categoryIdentifier.equals(book.categoryIdentifier))
        assertTrue(b?.description.equals(book.description))
        assertTrue(b?.imageUrl.equals(book.imageUrl))
        // setup viewModel
        val model = BookDetailViewModel(title, mockBooksRepo)
        assertTrue(b?.title.equals(model.book.value?.title))
        assertTrue(b?.author.equals(model.book.value?.author))
        assertTrue(b?.categoryIdentifier.equals(model.book.value?.categoryIdentifier))
        assertTrue(b?.description.equals(model.book.value?.description))
        assertTrue(b?.imageUrl.equals(model.book.value?.imageUrl))
        println("mock book = $b\nvm book = ${model.book.value}")
        println("\n")
    }

    @Test
    fun booksVM() {
        println( "booksVM() test running...")
        // setup mock repo
        val categoryId = "uuid"
        val mockBooksRepo = mock(BooksRepo::class.java)
        Mockito.`when`(mockBooksRepo.getBooks(categoryId)).thenReturn(bookList)
        val books = mockBooksRepo.getBooks(categoryId).value
        assertTrue(books?.size ?: 0 > 0)
        // setup viewModel
        val booksVM = BooksViewModel(categoryId, mockBooksRepo)
        booksVM.getData()
        assertEquals(booksVM.bookList.value?.size ?: 0, books?.size ?: 9)
        println("vm size = ${booksVM.bookList.value?.size}, repo size = ${books?.size}")
        println( "\n")
    }

   @Test
    fun categoryVM() {
       println( "categoryVM() test running...")
       // setup mock repo
       val mockCategoriesRepo = mock(CategoriesRepo::class.java)
       Mockito.`when`(mockCategoriesRepo.getCategories()).thenReturn(categoryList)
       val cats = mockCategoriesRepo.getCategories().value
       assertTrue(cats?.size ?: 0 > 0)
       // setup viewModel
       val catVM = CategoryViewModel(mockCategoriesRepo)
       catVM.getData()
       assertEquals(catVM.categories.value?.size ?: 0, cats?.size ?: 9)
       println("vm size = ${catVM.categories.value?.size}, repo size = ${cats?.size}")
       println( "\n")
    }

    @Test
    fun baseVM() {
        println( "baseVM() test running...")
        val baseVM = MockClass()
        // check defaults
        Assert.assertEquals(baseVM.showOfflineDinosaur.value, false)
        Assert.assertEquals(baseVM.showNoContentAvailable.value, false)
        Assert.assertEquals(baseVM.showLoading.value, false)

        baseVM.showOfflineDinosaur.postValue(true)
        Assert.assertEquals(baseVM.showOfflineDinosaur.value, true)
        baseVM.showNoContentAvailable.postValue(true)
        Assert.assertEquals(baseVM.showNoContentAvailable.value, true)
        baseVM.showLoading.postValue(true)
        Assert.assertEquals(baseVM.showLoading.value, true)
    }
}