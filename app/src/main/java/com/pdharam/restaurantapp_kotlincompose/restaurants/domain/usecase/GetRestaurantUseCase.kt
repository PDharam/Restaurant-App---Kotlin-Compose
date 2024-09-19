package com.pdharam.restaurantapp_kotlincompose.restaurants.domain.usecase

import com.pdharam.restaurantapp_kotlincompose.restaurants.data.repository.RestaurantDetailsRepository
import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.model.Restaurant
import javax.inject.Inject

class GetRestaurantUseCase @Inject constructor(private val repository: RestaurantDetailsRepository) {

    suspend operator fun invoke(id: Int): Restaurant? {
        repository.loadRestaurant(id)
        return repository.getRestaurant(id)
    }
}