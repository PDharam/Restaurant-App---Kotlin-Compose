package com.pdharam.restaurantapp_kotlincompose.restaurants.presentation.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdharam.restaurantapp_kotlincompose.restaurants.data.repository.RestaurantDetailsRepository
import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.model.Restaurant
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

class RestaurantDetailsViewModel(stateHandle: SavedStateHandle) : ViewModel() {
    private val _state: MutableState<Restaurant?> = mutableStateOf(null)
    val state: State<Restaurant?>
        get() = _state
    val repository = RestaurantDetailsRepository()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    init {

        val id = stateHandle.get<Int>("restaurant_id") ?: 0
        viewModelScope.launch(exceptionHandler) {
            try {
                repository.refreshCache(id)
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> if (repository.getRestaurant(id) == null) {
                        throw e
                    }
                }
            }

            _state.value = repository.getRestaurant(id)

        }

    }


}