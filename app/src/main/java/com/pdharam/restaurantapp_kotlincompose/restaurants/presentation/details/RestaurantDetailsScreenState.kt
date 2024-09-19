package com.pdharam.restaurantapp_kotlincompose.restaurants.presentation.details

import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.model.Restaurant

data class RestaurantDetailsScreenState(
    val restaurant: Restaurant? = null,
    val isLoading: Boolean,
    val error: String? = null
)
