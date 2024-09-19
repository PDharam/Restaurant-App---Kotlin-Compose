package com.pdharam.restaurantapp_kotlincompose

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class RestaurantViewModel : ViewModel() {
    private val repository = RestaurantsRepository()
    private val _state =
        mutableStateOf(RestaurantScreenState(restaurants = listOf(), isLoading = true))
    val state: State<RestaurantScreenState>
        get() = _state

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _state.value = _state.value.copy(error = throwable.message, isLoading = false)
        throwable.printStackTrace()
    }

    init {
        getRestaurants()
    }

    private fun getRestaurants() {

        viewModelScope.launch(coroutineExceptionHandler) {
            val restaurants = repository.getAllRestaurant()
            _state.value = _state.value.copy(restaurants = restaurants, isLoading = false)

        }

    }

    fun toggleFavourite(id: Int, oldValue: Boolean) {
        //cache this field in local database (ROOM -> SQLite)
        viewModelScope.launch(coroutineExceptionHandler) {
            val updatedList = repository.toggleFavouriteRestaurant(id, oldValue)
            _state.value = _state.value.copy(restaurants = updatedList)
        }
    }


}