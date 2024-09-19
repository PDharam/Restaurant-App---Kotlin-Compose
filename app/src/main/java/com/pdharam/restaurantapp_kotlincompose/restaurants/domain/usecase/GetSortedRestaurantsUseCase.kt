package com.pdharam.restaurantapp_kotlincompose.restaurants.domain.usecase

import com.pdharam.restaurantapp_kotlincompose.restaurants.data.repository.RestaurantsRepository
import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.model.Restaurant
import javax.inject.Inject

class GetSortedRestaurantsUseCase @Inject constructor(private val repository: RestaurantsRepository) {

    suspend operator fun invoke(): List<Restaurant> {
        return repository.getRestaurants().sortedBy { it.title }
    }
}