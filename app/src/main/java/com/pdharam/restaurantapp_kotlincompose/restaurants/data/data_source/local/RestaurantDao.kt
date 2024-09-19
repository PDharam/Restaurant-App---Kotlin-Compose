package com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface RestaurantDao {

    @Query("SELECT * FROM tbl_restaurant")
    suspend fun getAll(): List<LocalRestaurant>

    @Query("SELECT * FROM tbl_restaurant WHERE r_id = :id")
    suspend fun getRestaurant(id: Int): LocalRestaurant?

    @Query("SELECT * FROM tbl_restaurant WHERE is_favourite = 1")
    suspend fun getAllFavorite(): List<LocalRestaurant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(restaurants: List<LocalRestaurant>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurants: LocalRestaurant)

    @Update(entity = LocalRestaurant::class)
    suspend fun update(partialLocalRestaurant: PartialLocalRestaurant)

    @Update(entity = LocalRestaurant::class)
    suspend fun updateAll(partialLocalRestaurants: List<PartialLocalRestaurant>)

}