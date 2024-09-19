package com.pdharam.restaurantapp_kotlincompose.restaurants.domain.model

data class Restaurant(
    val id: Int,
    val title: String,
    val description: String,
    val isFavourite: Boolean = false
)
