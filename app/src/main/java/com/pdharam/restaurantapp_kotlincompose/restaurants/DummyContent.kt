package com.pdharam.restaurantapp_kotlincompose.restaurants

import com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.remote.RemoteRestaurant
import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.model.Restaurant

object DummyContent {

    fun getDummyRestaurants() = listOf(
        Restaurant(0, "title0", "description0", false),

        Restaurant(1, "title1", "description1", false),

        Restaurant(2, "title2", "description2", false),

        Restaurant(3, "title3", "description3", false)
    )

    fun getRemoteRestaurants() = getDummyRestaurants().map {
        RemoteRestaurant(it.id, it.title, it.description)
    }

    fun getDomainRestaurants() = getDummyRestaurants().toMutableList()

}