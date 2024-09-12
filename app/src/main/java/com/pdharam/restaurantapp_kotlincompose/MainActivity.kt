package com.pdharam.restaurantapp_kotlincompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.pdharam.restaurantapp_kotlincompose.ui.theme.RestaurantAppKotlinComposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RestaurantAppKotlinComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RestaurantApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Composable
    fun RestaurantApp(modifier: Modifier = Modifier) {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "restaurants") {
            composable(route = "restaurants") {
                RestaurantsScreen { id ->
                    navController.navigate("restaurants/$id")
                }
            }
            composable(
                route = "restaurants/{restaurant_id}",
                arguments = listOf(navArgument("restaurant_id") {
                    type = NavType.IntType
                }), deepLinks = listOf(navDeepLink {
                    uriPattern = "www.restaurantsapp.details.com/{restaurant_id}"
                })
            ) { navStackEntry ->
                val id = navStackEntry.arguments?.getInt("restaurant_id")
                //We will not use this id but instead of this we will get this from SavedStateHandle obj - inside direct in ViewModel
                //Behind the scenes, the Navigation component saves the navigation arguments stored in NavStackEntry into SavedStateHandle,
                RestaurantDetailsScreen()
            }
        }

    }
}

