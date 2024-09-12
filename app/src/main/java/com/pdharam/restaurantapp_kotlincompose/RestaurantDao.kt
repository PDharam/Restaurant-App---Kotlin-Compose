package com.pdharam.restaurantapp_kotlincompose

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface RestaurantDao {

    @Query("SELECT * FROM tbl_restaurant")
    suspend fun getAll(): List<Restaurant>

    @Query("SELECT * FROM tbl_restaurant WHERE r_id = :id")
    suspend fun getRestaurant(id: Int): Restaurant?

    @Query("SELECT * FROM tbl_restaurant WHERE is_favourite = 1")
    suspend fun getAllFavorite(): List<Restaurant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(restaurants: List<Restaurant>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurants: Restaurant)

    @Update(entity = Restaurant::class)
    suspend fun update(partialRestaurant: PartialRestaurant)

    @Update(entity = Restaurant::class)
    suspend fun updateAll(partialRestaurants: List<PartialRestaurant>)

}