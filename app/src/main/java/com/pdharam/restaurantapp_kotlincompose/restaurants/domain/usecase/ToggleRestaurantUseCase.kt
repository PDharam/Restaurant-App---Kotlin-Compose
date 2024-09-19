package com.pdharam.restaurantapp_kotlincompose.restaurants.domain.usecase

import com.pdharam.restaurantapp_kotlincompose.restaurants.data.repository.RestaurantsRepository
import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.model.Restaurant

class ToggleRestaurantUseCase {
    private val repository: RestaurantsRepository = RestaurantsRepository()

    suspend operator fun invoke(id: Int, oldValue: Boolean): List<Restaurant> {
        val isFav = oldValue.not()
        repository.toggleFavouriteRestaurant(id, isFav)
        return GetSortedRestaurantUseCase().invoke()
    }
}