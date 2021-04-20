package com.lekalina.nytbestsellers.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lekalina.nytbestsellers.NYT
import com.lekalina.nytbestsellers.data.Book
import com.lekalina.nytbestsellers.data.Category

@Database(entities = [Category::class, Book::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun booksDao(): BooksDao

    companion object {
        val db = Room.databaseBuilder(NYT.appContext, AppDatabase::class.java, "nyt-bestsellers").build()
    }
}