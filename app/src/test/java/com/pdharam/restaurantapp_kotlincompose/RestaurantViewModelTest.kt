package com.pdharam.restaurantapp_kotlincompose

import com.pdharam.restaurantapp_kotlincompose.restaurants.DummyContent
import com.pdharam.restaurantapp_kotlincompose.restaurants.data.repository.RestaurantsRepository
import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.usecase.GetInitialRestaurantsUseCase
import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.usecase.GetSortedRestaurantsUseCase
import com.pdharam.restaurantapp_kotlincompose.restaurants.domain.usecase.ToggleRestaurantUseCase
import com.pdharam.restaurantapp_kotlincompose.restaurants.presentation.list.RestaurantScreenState
import com.pdharam.restaurantapp_kotlincompose.restaurants.presentation.list.RestaurantViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class RestaurantViewModelTest {

    val testDispatcher = StandardTestDispatcher()
    val testScope = TestScope(testDispatcher)

//    @get:Rule
//    val instantTaskExecutor = InstantTaskExecutorRule()


    @Test
    fun initialState_isProduced() = testScope.runTest {
        val viewModel: RestaurantViewModel = getRestaurantViewModel()
        val initialState = viewModel.state.value

        assert(
            initialState == RestaurantScreenState(
                restaurants = emptyList(),
                isLoading = true,
                error = null
            )
        )
    }

    @Test
    fun stateWithContent_isProduced() = testScope.runTest {
        val viewModel = getRestaurantViewModel()

        advanceUntilIdle()

        val currentState = viewModel.state.value
        assert(
            currentState == RestaurantScreenState(
                restaurants = DummyContent.getDummyRestaurants(),
                isLoading = false,
                error = null
            )
        )
    }

    @Test
    fun stateWithError_isProduced() = testScope.runTest {
        val viewModel = getRestaurantViewModel()

        advanceUntilIdle()
        val currentState = viewModel.state.value

        assert(
            currentState == RestaurantScreenState(
                restaurants = DummyContent.getDummyRestaurants(),
                isLoading = false,
                error = null
            )
        )
    }

    private fun getRestaurantViewModel(): RestaurantViewModel {
        val fakeApiService = FakeApiService()
        val fakeRestaurantDao = FakeRestaurantDao()

        val repository = RestaurantsRepository(fakeApiService, fakeRestaurantDao, testDispatcher)
        val getSortedRestaurantsUseCase = GetSortedRestaurantsUseCase(repository)
        val getInitialRestaurantsUseCase =
            GetInitialRestaurantsUseCase(repository, getSortedRestaurantsUseCase)
        val toggleRestaurantUseCase =
            ToggleRestaurantUseCase(repository, getSortedRestaurantsUseCase)
        return RestaurantViewModel(
            getInitialRestaurantsUseCase,
            toggleRestaurantUseCase,
            testDispatcher
        )
    }
}