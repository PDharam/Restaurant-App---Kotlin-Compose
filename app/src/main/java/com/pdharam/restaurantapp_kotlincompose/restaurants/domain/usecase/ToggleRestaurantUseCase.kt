package com.pdharam.restaurantapp_kotlincompose.restaurants.domain.usecase

import com.pdharam.restaurantapp_kotlincompose.restaurants.data.repository.RestaurantsRepository
import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.model.Restaurant
import javax.inject.Inject

class ToggleRestaurantUseCase @Inject constructor(
    private val repository: RestaurantsRepository,
    private val getSortedRestaurantsUseCase: GetSortedRestaurantsUseCase
) {

    suspend operator fun invoke(id: Int, oldValue: Boolean): List<Restaurant> {
        val isFav = oldValue.not()
        repository.toggleFavouriteRestaurant(id, isFav)
        return getSortedRestaurantsUseCase.invoke()
    }
}