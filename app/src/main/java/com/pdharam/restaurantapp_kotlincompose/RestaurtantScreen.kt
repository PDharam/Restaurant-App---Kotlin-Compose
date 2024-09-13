package com.pdharam.restaurantapp_kotlincompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun RestaurantsScreen(modifier: Modifier = Modifier, onItemClick: (id: Int) -> Unit) {
    val viewModel: RestaurantViewModel = viewModel()
    /*commented this due to don't need any more - we are calling API through init{} block of viewmodel.
    This launchedEffect useful when we don't want to call some code on recomposition just only one while initial composition happens
     */
//    LaunchedEffect(key1 = "request_restaurant") {
//        viewModel.getRestaurants()
//    }

    val state = viewModel.state.value

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(
                vertical = 8.dp, horizontal = 8.dp
            )
        ) {
            items(state.restaurants) { item: Restaurant ->
                RestaurantItem(
                    item = item,
                    onFavouriteClick = { id, oldValue -> viewModel.toggleFavourite(id, oldValue) },
                    onItemClick = { id -> onItemClick(id) })
            }
        }
        if (state.isLoading) {
            CircularProgressIndicator()
        }
        if (state.error != null) {
            Text(text = state.error)
        }
    }

}


@Composable
private fun RestaurantItem(
    item: Restaurant,
    onFavouriteClick: (id: Int, oldValue: Boolean) -> Unit,
    onItemClick: (id: Int) -> Unit
) {

    val favouriteIcon = if (item.isFavourite) {
        Icons.Filled.Favorite
    } else {
        Icons.Filled.FavoriteBorder
    }
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onItemClick(item.id) }
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .padding(8.dp)
        ) {
            RestaurantIcon(
                Icons.Filled.Place,
                Modifier
                    .weight(0.15f)
                    .size(40.dp)
            )
            RestaurantDetails(
                item.title, item.description,
                Modifier
                    .padding(8.dp)
                    .weight(0.70f)

            )
            RestaurantIcon(favouriteIcon, Modifier.weight(0.15f)) {
                onFavouriteClick(item.id, item.isFavourite)
            }
        }
    }
}


@Composable
fun RestaurantDetails(
    title: String,
    description: String,
    modifier: Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(modifier = modifier, horizontalAlignment = horizontalAlignment) {
        Text(
            text = title, style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.alpha(1f)
        )

    }
}

@Composable
fun RestaurantIcon(icon: ImageVector, modifier: Modifier, onClick: () -> Unit = {}) {
    Image(
        imageVector = icon,
        modifier = modifier
            .padding(8.dp)
            .clickable {
                onClick()
            },
        contentDescription = "Restaurant :Icon"
    )
}
