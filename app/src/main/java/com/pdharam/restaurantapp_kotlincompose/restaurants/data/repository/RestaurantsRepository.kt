package com.pdharam.restaurantapp_kotlincompose.restaurants.data.repository

import com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.local.LocalRestaurant
import com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.local.PartialLocalRestaurant
import com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.local.RestaurantDao
import com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.remote.RestaurantsApiService
import com.pdharam.restaurantapp_kotlincompose.restaurants.data.di.IoDispatcher
import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.model.Restaurant
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantsRepository @Inject constructor(
    private var restInterface: RestaurantsApiService,
    private val restaurantDao: RestaurantDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {


    init {
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://fir-project-s-default-rtdb.firebaseio.com/").build()
        restInterface = retrofit.create(RestaurantsApiService::class.java)
    }

    suspend fun loadRestaurants() {
        return withContext(dispatcher) {
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
        }

        /* NOTE:
        For Retrofit interface calls such as restInterface.getRestaurants(),
        we can skip wrapping them in withContext() blocks because Retrofit already does this behind the scenes
        and sets the Dispatchers.IO dispatcher for all suspending methods from within its interface.
        * */
    }

    private suspend fun refreshCache() {
        val remoteRestaurants = restInterface.getRestaurants()
        val favouriteRestaurants = restaurantDao.getAllFavorite()
        restaurantDao.insertAll(remoteRestaurants.map {
            LocalRestaurant(
                id = it.id,
                title = it.title,
                description = it.description,
                isFavourite = false
            )
        })
        restaurantDao.updateAll(favouriteRestaurants.map {
            PartialLocalRestaurant(
                it.id, it.isFavourite
            )
        })
    }

    suspend fun getRestaurants(): List<Restaurant> {
        return withContext(dispatcher) {
            return@withContext restaurantDao.getAll().map {
                Restaurant(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    isFavourite = it.isFavourite
                )
            }
        }
    }


    suspend fun toggleFavouriteRestaurant(id: Int, isFav: Boolean) {
        return withContext(dispatcher) {
            restaurantDao.update(PartialLocalRestaurant(id, isFav))
        }
    }

}