package com.pdharam.restaurantapp_kotlincompose.restaurants.presentation.list

import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.model.Restaurant

data class RestaurantScreenState(
    val restaurants: List<Restaurant>,
    val isLoading: Boolean,
    val error: String? = null
)