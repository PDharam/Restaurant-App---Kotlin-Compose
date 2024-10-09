package com.pdharam.restaurantapp_kotlincompose

import com.pdharam.restaurantapp_kotlincompose.restaurants.DummyContent
import com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.remote.RemoteRestaurant
import com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.remote.RestaurantsApiService

class FakeApiService : RestaurantsApiService {

    override suspend fun getRestaurants(): List<RemoteRestaurant> {
        return DummyContent.getRemoteRestaurants()
    }

    override suspend fun getRestaurant(id: Int): Map<String, RemoteRestaurant> {
        TODO()
    }
}