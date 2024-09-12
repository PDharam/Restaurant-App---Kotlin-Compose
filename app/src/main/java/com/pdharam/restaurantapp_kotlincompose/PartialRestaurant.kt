package com.pdharam.restaurantapp_kotlincompose

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
class PartialRestaurant(
    @ColumnInfo(name = "r_id")
    val id: Int,
    @ColumnInfo(name = "is_favourite")
    val isFavourite: Boolean
)
