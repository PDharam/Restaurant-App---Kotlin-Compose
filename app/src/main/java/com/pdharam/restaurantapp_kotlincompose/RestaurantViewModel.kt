package com.pdharam.restaurantapp_kotlincompose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantViewModel(
    private val stateHandle: SavedStateHandle
) : ViewModel() {
    private var restInterface: RestaurantsApiService
    val state = mutableStateOf(emptyList<Restaurant>())
    val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    init {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://fir-project-s-default-rtdb.firebaseio.com/")
            .build()
        restInterface = retrofit.create(RestaurantsApiService::class.java)
        getRestaurants()
    }

    private fun getRestaurants() {

        viewModelScope.launch(coroutineExceptionHandler) {

            val restaurants = getRemoteRestaurants()
            state.value = restaurants.restoreSelection()

        }

    }

    private suspend fun getRemoteRestaurants(): List<Restaurant> {
        return withContext(Dispatchers.IO) {
            restInterface.getRestaurants()
        }

        /* NOTE:
        For Retrofit interface calls such as restInterface.getRestaurants(),
        we can skip wrapping them in withContext() blocks because Retrofit already does this behind the scenes
        and sets the Dispatchers.IO dispatcher for all suspending methods from within its interface.
        * */
    }

    fun toggleFavourite(id: Int) {
        //take mutable list
        val restaurantList = state.value.toMutableList()
        //find index based on id property
        val itemIndex = restaurantList.indexOfFirst { it.id == id }
        //take particular item using received index
        val item = restaurantList[itemIndex]
        //update item using item.copy
        restaurantList[itemIndex] = item.copy(isFavourite = !item.isFavourite)
        storeSelection(restaurantList[itemIndex])
        //finally update the state
        state.value = restaurantList
    }

    private fun storeSelection(item: Restaurant) {
        //Take list of id's if list not available return  empty list - type mutable so we can update it.
        val savedToggled = stateHandle.get<List<Int>>(FAVOURITE).orEmpty().toMutableList()
        //If item is favourite then add into savedToggled list otherwise removed it from list.
        if (item.isFavourite) savedToggled.add(item.id) else savedToggled.remove(item.id)
        //Set updated list again in stateHandle obj.
        stateHandle[FAVOURITE] = savedToggled
    }

    private fun List<Restaurant>.restoreSelection(): List<Restaurant> {
        stateHandle.get<List<Int>>(FAVOURITE)?.let { selectedIds ->
            val restaurantMap = this.associateBy { it.id }

            selectedIds.forEach { id ->
                restaurantMap[id]?.isFavourite = true
            }

            return restaurantMap.values.toList()

        }

        return this
    }

    companion object {
        const val FAVOURITE = "favourite"
    }

}