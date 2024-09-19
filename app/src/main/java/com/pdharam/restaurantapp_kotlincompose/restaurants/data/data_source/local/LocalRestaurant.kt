package com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_restaurant")
data class LocalRestaurant(
    @PrimaryKey
    @ColumnInfo(name = "r_id")
    val id: Int,
    @ColumnInfo(name = "r_title")
    val title: String,
    @ColumnInfo(name = "r_description")
    val description: String,
    @ColumnInfo(name = "is_favourite")
    val isFavourite: Boolean = false
)
