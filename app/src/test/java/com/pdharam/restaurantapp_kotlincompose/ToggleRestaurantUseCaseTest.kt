package com.pdharam.restaurantapp_kotlincompose

import com.pdharam.restaurantapp_kotlincompose.restaurants.DummyContent
import com.pdharam.restaurantapp_kotlincompose.restaurants.data.repository.RestaurantsRepository
import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.usecase.GetSortedRestaurantsUseCase
import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.usecase.ToggleRestaurantUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ToggleRestaurantUseCaseTest {
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)


    @Test
    fun toggleRestaurant_IsUpdatingFavouriteField() = testScope.runTest {

        //Setup UseCase
        val restaurantRepository =
            RestaurantsRepository(FakeApiService(), FakeRestaurantDao(), testDispatcher)
        val getSortedRestaurantsUseCase = GetSortedRestaurantsUseCase(restaurantRepository)
        val useCase = ToggleRestaurantUseCase(restaurantRepository, getSortedRestaurantsUseCase)

        //Pre Load Restaurants
        restaurantRepository.loadRestaurants()
        advanceUntilIdle()

        //Execute Use Case
        val restaurants = DummyContent.getDomainRestaurants()
        val targetRestaurant = restaurants[0]
        val isFavourite = targetRestaurant.isFavourite
        println("Existing Restaurant List: $restaurants")
        println("Before update IsFavourite: $isFavourite")
        val updatedRestaurants = useCase(targetRestaurant.id, isFavourite)
        println("Updated Restaurant List $updatedRestaurants")
        advanceUntilIdle()

        restaurants[0] = targetRestaurant.copy(isFavourite = !isFavourite)
        println("Old IsFavourite:  ${restaurants[0]}  New Favourite: ${updatedRestaurants[0]}")
        assert(updatedRestaurants == restaurants)

    }

}