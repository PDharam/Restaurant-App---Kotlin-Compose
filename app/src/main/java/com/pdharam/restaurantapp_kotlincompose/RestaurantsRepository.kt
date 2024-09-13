package com.pdharam.restaurantapp_kotlincompose

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException

class RestaurantsRepository {
    private var restInterface: RestaurantsApiService
    private val restaurantDao: RestaurantDao =
        RestaurantDb.getDaoInstance(RestaurantApplication.getAppContext())

    init {
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://fir-project-s-default-rtdb.firebaseio.com/").build()
        restInterface = retrofit.create(RestaurantsApiService::class.java)
    }

    suspend fun getAllRestaurant(): List<Restaurant> {
        return withContext(Dispatchers.IO) {
            try {
                refreshCache()
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException, is ConnectException, is HttpException -> if (restaurantDao.getAll()
                            .isEmpty()
                    ) {
                        throw Exception(
                            "Something went wrong" + "We have no data"
                        )
                    }

                    else -> throw e
                }
            }

            return@withContext restaurantDao.getAll()
        }

        /* NOTE:
        For Retrofit interface calls such as restInterface.getRestaurants(),
        we can skip wrapping them in withContext() blocks because Retrofit already does this behind the scenes
        and sets the Dispatchers.IO dispatcher for all suspending methods from within its interface.
        * */
    }

    private suspend fun refreshCache() {
        val restaurants = restInterface.getRestaurants()
        val favouriteRestaurants = restaurantDao.getAllFavorite()
        restaurantDao.insertAll(restaurants)
        restaurantDao.updateAll(favouriteRestaurants.map {
            PartialRestaurant(
                it.id, it.isFavourite
            )
        })
    }


    suspend fun toggleFavouriteRestaurant(id: Int, oldValue: Boolean): List<Restaurant> {
        return withContext(Dispatchers.IO) {
            restaurantDao.update(PartialRestaurant(id, !oldValue))
            restaurantDao.getAll()
        }
    }

}