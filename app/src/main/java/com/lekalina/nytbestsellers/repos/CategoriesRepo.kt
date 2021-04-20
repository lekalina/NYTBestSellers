package com.lekalina.nytbestsellers.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.lekalina.nytbestsellers.api.RestCaller
import com.lekalina.nytbestsellers.data.Category
import com.lekalina.nytbestsellers.db.AppDatabase.Companion.db
import kotlinx.coroutines.Dispatchers


class CategoriesRepo {

    /**
     * Method to get categories which maintains single source of truth through the database
     * and updates the database with the latest network data
     */
    fun getCategories(): LiveData<List<Category>> = liveData(Dispatchers.IO) {
        db.categoriesDao().getCategories().let { cats -> emit(cats) }
        val response = RestCaller().getService().getBookCategories()
        val categories = response.body()
        if(response.isSuccessful && categories?.status.equals("OK")) {
            categories?.results?.let { netCats ->
                db.categoriesDao().insertAll(netCats)
                db.categoriesDao().getCategories().let { cats -> emit(cats) }
            }
        }
    }
}
