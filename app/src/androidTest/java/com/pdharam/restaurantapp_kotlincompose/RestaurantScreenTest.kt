package com.pdharam.restaurantapp_kotlincompose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.pdharam.restaurantapp_kotlincompose.restaurants.DummyContent
import com.pdharam.restaurantapp_kotlincompose.restaurants.presentation.list.Description
import com.pdharam.restaurantapp_kotlincompose.restaurants.presentation.list.RestaurantScreenState
import com.pdharam.restaurantapp_kotlincompose.restaurants.presentation.list.RestaurantsScreen
import com.pdharam.restaurantapp_kotlincompose.ui.theme.RestaurantAppKotlinComposeTheme
import org.junit.Rule
import org.junit.Test

class RestaurantScreenTest {

    @get:Rule
    val testRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun initialState_isRendered() {
        testRule.setContent {
            RestaurantAppKotlinComposeTheme {
                RestaurantsScreen(
                    state = RestaurantScreenState(
                        restaurants = emptyList(),
                        isLoading = true
                    ), onItemClick = {},
                    onFavouriteClick = { _: Int, _: Boolean -> }
                )
            }
        }

        testRule.onNodeWithContentDescription(
            Description.RESTAURANTS_LOADING
        ).assertIsDisplayed()
    }

    @Test
    fun stateWithContent_isRendered() {
        val dummyRestaurants = DummyContent.getDummyRestaurants()
        testRule.setContent {
            RestaurantAppKotlinComposeTheme {
                RestaurantsScreen(state = RestaurantScreenState(
                    restaurants = dummyRestaurants,
                    isLoading = false
                ), onItemClick = {}, onFavouriteClick = { _: Int, _: Boolean -> })
            }
        }

        testRule.onNodeWithText(dummyRestaurants[0].title)
            .assertIsDisplayed()

        testRule.onNodeWithText(dummyRestaurants[0].description)
            .assertIsDisplayed()

        testRule.onNodeWithContentDescription(
            Description.RESTAURANTS_LOADING
        ).assertDoesNotExist()
    }

    @Test
    fun stateWithError_isRendered() {
        val errorText = "Something went wrong"
        testRule.setContent {
            RestaurantAppKotlinComposeTheme {
                RestaurantsScreen(
                    state = RestaurantScreenState(
                        restaurants = emptyList(),
                        isLoading = false,
                        error = errorText
                    ), onItemClick = {}, onFavouriteClick = { _: Int, _: Boolean -> })
            }
        }

        testRule.onNodeWithText(errorText)
            .assertIsDisplayed()
        testRule.onNodeWithContentDescription(
            Description.RESTAURANTS_LOADING
        ).assertDoesNotExist()
    }

    @Test
    fun stateWithContent_clickOnItem_isRegistered() {
        val restaurants = DummyContent.getDummyRestaurants()
        val targetRestaurant = restaurants.get(0)

        testRule.setContent {
            RestaurantAppKotlinComposeTheme {
                RestaurantsScreen(
                    state = RestaurantScreenState(
                        restaurants = restaurants,
                        isLoading = false
                    ), onItemClick = { id ->
                        assert(id == targetRestaurant.id)

                    }, onFavouriteClick = { _: Int, _: Boolean -> })

            }
        }

        testRule.onNodeWithText(targetRestaurant.title)
            .performClick()
    }

}