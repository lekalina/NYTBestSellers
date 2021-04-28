package com.lekalina.nytbestsellers.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class Categories(
    @SerializedName("status")
    var status: String?,
    @SerializedName("num_results")
    var count: Int?,
    @SerializedName("results")
    var results: List<Category>?
)

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    @SerializedName("list_name_encoded")
    @ColumnInfo(name = "id") var identifier: String,
    @SerializedName("list_name")
    @ColumnInfo(name = "name") var name: String?,
    @SerializedName("display_name")
    @ColumnInfo(name = "display_name") var displayName: String?
)
