package com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
class PartialLocalRestaurant(
    @ColumnInfo(name = "r_id")
    val id: Int,
    @ColumnInfo(name = "is_favourite")
    val isFavourite: Boolean
)
