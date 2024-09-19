package com.pdharam.restaurantapp_kotlincompose.restaurants.presentation.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.usecase.GetInitialRestaurantsUseCase
import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.usecase.ToggleRestaurantUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class RestaurantViewModel : ViewModel() {
    val getInitialRestaurantsUseCase = GetInitialRestaurantsUseCase()
    val toggleRestaurantUseCase = ToggleRestaurantUseCase()
    private val _state =
        mutableStateOf(RestaurantScreenState(restaurants = listOf(), isLoading = true))
    val state: State<RestaurantScreenState>
        get() = _state

    val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        _state.value = _state.value.copy(error = throwable.message, isLoading = false)
        throwable.printStackTrace()
    }

    init {
        getRestaurants()
    }

    private fun getRestaurants() {

        viewModelScope.launch(coroutineExceptionHandler) {
            val restaurants = getInitialRestaurantsUseCase()
            _state.value = _state.value.copy(restaurants = restaurants, isLoading = false)

        }

    }

    fun toggleFavourite(id: Int, oldValue: Boolean) {
        //cache this field in local database (ROOM -> SQLite)
        viewModelScope.launch(coroutineExceptionHandler) {
            val updatedList = toggleRestaurantUseCase(id, oldValue)
            _state.value = _state.value.copy(restaurants = updatedList)
        }
    }


}