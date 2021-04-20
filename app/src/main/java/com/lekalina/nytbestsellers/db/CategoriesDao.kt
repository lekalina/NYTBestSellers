package com.lekalina.nytbestsellers.db

import androidx.room.*
import com.lekalina.nytbestsellers.data.Category

@Dao
interface CategoriesDao {

    @Query("SELECT * FROM categories ORDER BY id ASC")
    suspend fun getCategories(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<Category>)

    @Delete
    suspend fun delete(category: Category)
}