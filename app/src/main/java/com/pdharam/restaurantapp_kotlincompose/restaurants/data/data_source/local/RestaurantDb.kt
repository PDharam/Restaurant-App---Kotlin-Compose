package com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 3, entities = [LocalRestaurant::class], exportSchema = false)
abstract class RestaurantDb : RoomDatabase() {
    abstract val restaurantDao: RestaurantDao

}