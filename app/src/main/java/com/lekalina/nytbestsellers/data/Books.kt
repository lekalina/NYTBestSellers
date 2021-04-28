package com.lekalina.nytbestsellers.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class BooksResponse (
    @SerializedName("status")
    var status: String?,
    @SerializedName("num_results")
    var count: Int?,
    @SerializedName("results")
    var results: Books?
)

data class Books(
    @SerializedName("list_name")
    var categoryName: String?,
    @SerializedName("list_name_encoded")
    var categoryIdentifier: String?,
    @SerializedName("books")
    var books: List<Book>?
)

@Entity(tableName = "books")
data class Book(
    @PrimaryKey
    @SerializedName("title")
    @ColumnInfo(name = "title") @NonNull var title: String,
    @SerializedName("author")
    @ColumnInfo(name = "author") var author: String?,
    @SerializedName("book_image")
    @ColumnInfo(name = "image_url") var imageUrl: String?,
    @SerializedName("description")
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "category_id") @Transient var categoryIdentifier: String?
)

