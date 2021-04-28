package com.lekalina.nytbestsellers.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.lekalina.nytbestsellers.api.RestCaller
import com.lekalina.nytbestsellers.data.Category
import com.lekalina.nytbestsellers.db.AppDatabase
import java.lang.Exception


class CategoriesRepo(private val caller: RestCaller = RestCaller()) {

    /**
     * Method to get categories which maintains single source of truth through the database
     * and updates the database with the latest network data
     */
    fun getCategories(): LiveData<List<Category>> = liveData {
        val db = AppDatabase.getInstance()
        val dbCats = db.categoriesDao().getCategories()
        dbCats.let { cats ->
            if (cats.isNotEmpty()) {
                // only emit if has content, otherwise wait for network response
                emit(cats)
            }
        }
        try {
            val response = caller.service.getBookCategories()
            response.results?.let { netCats ->
                db.categoriesDao().insertAll(netCats)
                db.categoriesDao().getCategories().let { cats -> emit(cats) }
            }
            if(response.results?.isEmpty() != false && dbCats.isEmpty()) {
                // emit an empty result if no data
                emit(ArrayList<Category>())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (dbCats.isEmpty()) {
                // emit an empty result if no data
                emit(ArrayList<Category>())
            }
        }
    }
}
