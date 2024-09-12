package com.pdharam.restaurantapp_kotlincompose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
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

class RestaurantDetailsViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {
    val state: MutableState<Restaurant?> = mutableStateOf(null)
    private var restInterface: RestaurantsApiService
    private val restaurantDao: RestaurantDao =
        RestaurantDb.getDaoInstance(RestaurantApplication.getAppContext())
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    init {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://fir-project-s-default-rtdb.firebaseio.com/")
            .build()
        restInterface = retrofit.create(RestaurantsApiService::class.java)
        val id = stateHandle.get<Int>("restaurant_id") ?: 0
        viewModelScope.launch(exceptionHandler) {
            try {
                refreshCache(id)
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> if (restaurantDao.getRestaurant(id) == null) {
                        throw e
                    }
                }
            }

            state.value = restaurantDao.getRestaurant(id)

        }

    }

    private suspend fun refreshCache(id: Int) {
        val restaurant = getRemoteRestaurant(id)
        restaurantDao.insertRestaurant(restaurant)
    }

    private suspend fun getRemoteRestaurant(id: Int): Restaurant {
        return withContext(Dispatchers.IO) {
            val responseMap = restInterface.getRestaurant(id)
            return@withContext responseMap.values.first()
        }
    }
}