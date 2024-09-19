package com.pdharam.restaurantapp_kotlincompose.restaurants.domain.usecase

import com.pdharam.restaurantapp_kotlincompose.restaurants.data.repository.RestaurantsRepository
import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.model.Restaurant
import javax.inject.Inject

class GetInitialRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantsRepository,
    private val getSortedRestaurantsUseCase: GetSortedRestaurantsUseCase
) {


    suspend operator fun invoke(): List<Restaurant> {
        repository.loadRestaurants()
        return getSortedRestaurantsUseCase()
    }
}