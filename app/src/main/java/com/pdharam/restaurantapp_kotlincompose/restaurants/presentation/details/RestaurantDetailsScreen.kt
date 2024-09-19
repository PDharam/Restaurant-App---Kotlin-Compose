package com.pdharam.restaurantapp_kotlincompose.restaurants.presentation.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pdharam.restaurantapp_kotlincompose.restaurants.presentation.list.RestaurantDetails
import com.pdharam.restaurantapp_kotlincompose.restaurants.presentation.list.RestaurantIcon

@Composable
fun RestaurantDetailsScreen(modifier: Modifier = Modifier, state: RestaurantDetailsScreenState?) {


    if (state != null) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RestaurantIcon(
                    icon = Icons.Filled.Place,
                    modifier = Modifier.padding(top = 32.dp, bottom = 32.dp)
                )
                RestaurantDetails(
                    title = state.restaurant?.title ?: "",
                    description = state.restaurant?.description ?: "",
                    modifier = Modifier.padding(bottom = 32.dp),
                    Alignment.CenterHorizontally
                )
                Text(text = "More info coming soon...")
            }
            if (state.isLoading) {
                CircularProgressIndicator()
            }
            if (state.error != null) {
                Text(text = state.error)
            }
        }
    }

}