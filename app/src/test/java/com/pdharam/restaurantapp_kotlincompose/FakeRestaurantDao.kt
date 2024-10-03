package com.pdharam.restaurantapp_kotlincompose

import com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.local.LocalRestaurant
import com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.local.PartialLocalRestaurant
import com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.local.RestaurantDao
import kotlinx.coroutines.delay

class FakeRestaurantDao : RestaurantDao {
    private var restaurantHashMap = HashMap<Int, LocalRestaurant>()

    override suspend fun getAll(): List<LocalRestaurant> {
        delay(1000)
        return restaurantHashMap.values.toList()
    }

    override suspend fun getRestaurant(id: Int): LocalRestaurant? {
        return restaurantHashMap[id]
    }

    override suspend fun getAllFavorite(): List<LocalRestaurant> {
        delay(1000)
        return restaurantHashMap.values.toList().filter { it.isFavourite }
    }

    override suspend fun insertAll(restaurants: List<LocalRestaurant>) {
        restaurants.forEach {
            restaurantHashMap[it.id] = it
        }
    }

    override suspend fun insertRestaurant(restaurants: LocalRestaurant) {
        restaurantHashMap[restaurants.id] = restaurants
    }

    override suspend fun update(partialLocalRestaurant: PartialLocalRestaurant) {
        delay(1000)
        updatePartialRestaurant(partialLocalRestaurant)
    }


    override suspend fun updateAll(partialLocalRestaurants: List<PartialLocalRestaurant>) {
        delay(1000)
        partialLocalRestaurants.forEach {
            updatePartialRestaurant(it)
        }
    }

    private fun updatePartialRestaurant(partialLocalRestaurant: PartialLocalRestaurant) {
        val restaurant = restaurantHashMap[partialLocalRestaurant.id]

        if (restaurant != null) {
            restaurantHashMap[partialLocalRestaurant.id] =
                restaurant.copy(isFavourite = partialLocalRestaurant.isFavourite)
        }
    }
}