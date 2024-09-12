package com.pdharam.restaurantapp_kotlincompose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException

class RestaurantViewModel : ViewModel() {
    private var restInterface: RestaurantsApiService
    private val restaurantDao: RestaurantDao =
        RestaurantDb.getDaoInstance(RestaurantApplication.getAppContext())
    val state = mutableStateOf(emptyList<Restaurant>())
    val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    init {
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://fir-project-s-default-rtdb.firebaseio.com/").build()
        restInterface = retrofit.create(RestaurantsApiService::class.java)
        getRestaurants()
    }

    private fun getRestaurants() {

        viewModelScope.launch(coroutineExceptionHandler) {
            state.value = getAllRestaurant()

        }

    }

    private suspend fun getAllRestaurant(): List<Restaurant> {
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

    fun toggleFavourite(id: Int, oldValue: Boolean) {
        //cache this field in local database (ROOM -> SQLite)
        viewModelScope.launch(coroutineExceptionHandler) {
            val restaurants = toggleFavouriteRestaurant(id, oldValue)
            state.value = restaurants
        }
    }

    private suspend fun toggleFavouriteRestaurant(id: Int, oldValue: Boolean): List<Restaurant> {
        return withContext(Dispatchers.IO) {
            restaurantDao.update(PartialRestaurant(id, !oldValue))
            restaurantDao.getAll()
        }
    }


}