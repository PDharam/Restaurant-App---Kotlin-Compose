package com.pdharam.restaurantapp_kotlincompose.restaurants.data.repository

import com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.local.LocalRestaurant
import com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.local.RestaurantDao
import com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.remote.RestaurantsApiService
import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.model.Restaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantDetailsRepository @Inject constructor(
    private var restInterface: RestaurantsApiService,
    private val restaurantDao: RestaurantDao
) {


    suspend fun loadRestaurant(id: Int) {
        val localRestaurant = getRemoteRestaurant(id)
        restaurantDao.insertRestaurant(localRestaurant)
    }

    private suspend fun getRemoteRestaurant(id: Int): LocalRestaurant {
        return withContext(Dispatchers.IO) {
            val responseMap = restInterface.getRestaurant(id)
            return@withContext responseMap.values.first().let {
                LocalRestaurant(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    isFavourite = false
                )
            }
        }
    }

    suspend fun getRestaurant(id: Int) = restaurantDao.getRestaurant(id)?.let {
        Restaurant(
            id = it.id,
            title = it.title,
            description = it.description,
            isFavourite = it.isFavourite
        )
    }
}