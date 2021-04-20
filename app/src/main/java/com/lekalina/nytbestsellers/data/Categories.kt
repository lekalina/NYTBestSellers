package com.lekalina.nytbestsellers.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class Categories(
    @field:SerializedName("status")
    var status: String?,
    @field:SerializedName("num_results")
    var count: Int?,
    @field:SerializedName("results")
    var results: List<Category>?
)

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    @field:SerializedName("list_name_encoded")
    @ColumnInfo(name = "id") var identifier: String,
    @field:SerializedName("list_name")
    @ColumnInfo(name = "name") var name: String?,
    @field:SerializedName("display_name")
    @ColumnInfo(name = "display_name") var display_name: String?
)
