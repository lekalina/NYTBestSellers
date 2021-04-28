package com.lekalina.nytbestsellers.db

import android.content.Context
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

    /**
     * Database instance is created as a singleton so that only a single instance exists.
     * "Each RoomDatabase instance is fairly expensive, and you rarely need access to
     * multiple instances within a single process." https://developer.android.com/training/data-storage/room
     * Using the singleton model vs the static singleton model, the instance is created
     * once it is used instead of on app creation
     */
    companion object {

        @Volatile private var dbInstance: AppDatabase? = null

        fun getInstance(): AppDatabase = dbInstance ?: synchronized(this) {
            dbInstance ?: buildDatabase(NYT.context).also { dbInstance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "nyt-bestsellers").build()
    }
}