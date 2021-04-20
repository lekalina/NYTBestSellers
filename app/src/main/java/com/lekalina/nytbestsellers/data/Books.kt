package com.lekalina.nytbestsellers.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class BooksResponse (
    @field:SerializedName("status")
    var status: String?,
    @field:SerializedName("num_results")
    var count: Int?,
    @field:SerializedName("results")
    var results: Books?
)

data class Books(
    @field:SerializedName("list_name")
    var category_name: String?,
    @field:SerializedName("list_name_encoded")
    var category_identifier: String?,
    @field:SerializedName("books")
    var books: List<Book>?
)

@Entity(tableName = "books")
data class Book(
    @PrimaryKey
    @field:SerializedName("title")
    @ColumnInfo(name = "title") @NonNull var title: String,
    @field:SerializedName("author")
    @ColumnInfo(name = "author") var author: String?,
    @field:SerializedName("book_image")
    @ColumnInfo(name = "image_url") var image_url: String?,
    @field:SerializedName("description")
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "category_id") @Transient var category_identifier: String?
)

