package com.pdharam.restaurantapp_kotlincompose.restaurants.presentation.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.usecase.GetRestaurantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailsViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val getRestaurantUseCase: GetRestaurantUseCase
) : ViewModel() {
    private val _state: MutableState<RestaurantDetailsScreenState> = mutableStateOf(
        RestaurantDetailsScreenState(isLoading = true)
    )
    val state: State<RestaurantDetailsScreenState>
        get() = _state


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _state.value = _state.value.copy(isLoading = true, error = throwable.message)
        throwable.printStackTrace()
    }

    init {

        val id = stateHandle.get<Int>("restaurant_id") ?: 0
        viewModelScope.launch(exceptionHandler) {
            _state.value = RestaurantDetailsScreenState(
                restaurant = getRestaurantUseCase(id),
                isLoading = false
            )

        }

    }


}