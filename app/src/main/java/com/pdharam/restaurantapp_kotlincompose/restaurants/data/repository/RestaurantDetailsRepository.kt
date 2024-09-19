package com.pdharam.restaurantapp_kotlincompose.restaurants.data.repository

import com.pdharam.restaurantapp_kotlincompose.RestaurantApplication
import com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.local.LocalRestaurant
import com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.local.RestaurantDao
import com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.local.RestaurantDb
import com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.remote.RestaurantsApiService
import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.model.Restaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantDetailsRepository {
    private var restInterface: RestaurantsApiService
    private val restaurantDao: RestaurantDao =
        RestaurantDb.getDaoInstance(RestaurantApplication.getAppContext())

    init {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://fir-project-s-default-rtdb.firebaseio.com/")
            .build()
        restInterface = retrofit.create(RestaurantsApiService::class.java)
    }


    suspend fun refreshCache(id: Int) {
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