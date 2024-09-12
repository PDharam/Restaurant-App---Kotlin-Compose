package com.pdharam.restaurantapp_kotlincompose

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tbl_restaurant")
data class Restaurant(
    @PrimaryKey
    @ColumnInfo(name = "r_id")
    @SerializedName("r_id")
    val id: Int,
    @ColumnInfo(name = "r_title")
    @SerializedName("r_title")
    val title: String,
    @ColumnInfo(name = "r_description")
    @SerializedName("r_description")
    val description: String,
    @ColumnInfo(name = "is_favourite")
    val isFavourite: Boolean = false
)
