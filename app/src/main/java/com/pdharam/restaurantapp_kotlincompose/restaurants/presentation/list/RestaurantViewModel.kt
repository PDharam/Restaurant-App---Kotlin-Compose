package com.pdharam.restaurantapp_kotlincompose.restaurants.presentation.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdharam.restaurantapp_kotlincompose.restaurants.data.di.MainDispatcher
import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.usecase.GetInitialRestaurantsUseCase
import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.usecase.ToggleRestaurantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val getInitialRestaurantsUseCase: GetInitialRestaurantsUseCase,
    private val toggleRestaurantUseCase: ToggleRestaurantUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

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

    fun getRestaurants() {

        viewModelScope.launch(dispatcher + coroutineExceptionHandler) {
            val restaurants = getInitialRestaurantsUseCase()
            println("ViewModel: Rest List: ${restaurants} ${Thread.currentThread().name}")
            _state.value = _state.value.copy(restaurants = restaurants, isLoading = false)
            println("ViewModel: State Value: ${state.value}")

        }

    }

    fun toggleFavourite(id: Int, oldValue: Boolean) {
        //cache this field in local database (ROOM -> SQLite)
        viewModelScope.launch(dispatcher + coroutineExceptionHandler) {
            val updatedList = toggleRestaurantUseCase(id, oldValue)
            _state.value = _state.value.copy(restaurants = updatedList)
        }
    }


}