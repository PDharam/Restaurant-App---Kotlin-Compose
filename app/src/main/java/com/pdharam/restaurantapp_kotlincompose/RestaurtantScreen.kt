package com.pdharam.restaurantapp_kotlincompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pdharam.restaurantapp_kotlincompose.ui.theme.RestaurantAppKotlinComposeTheme


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RestaurantAppKotlinComposeTheme {
        RestaurantsScreen()
    }

}

@Composable
fun RestaurantsScreen() {

    LazyColumn(
        contentPadding = PaddingValues(
            vertical = 8.dp, horizontal = 8.dp
        )
    ) {
        items(dummyRestaurants) { restaurant ->
            RestaurantItem(item = restaurant)
        }
    }

}


@Composable
fun RestaurantItem(item: Restaurant) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
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
        }
    }
}


@Composable
fun RestaurantDetails(title: String, description: String, modifier: Modifier) {
    Column(modifier = modifier) {
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
fun RestaurantIcon(icon: ImageVector, modifier: Modifier) {
    Image(
        imageVector = icon,
        modifier = modifier
            .padding(8.dp),
        contentDescription = "Restaurant :Icon"
    )
}
