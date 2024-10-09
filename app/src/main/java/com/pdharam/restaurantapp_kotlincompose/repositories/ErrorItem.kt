package com.pdharam.restaurantapp_kotlincompose.repositories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ErrorItem(message: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(

        modifier = modifier.padding(16.dp),

        horizontalArrangement =

        Arrangement.SpaceBetween,

        verticalAlignment = Alignment.CenterVertically

    ) {

        Text(

            text = message,

            maxLines = 2,

            modifier = Modifier.weight(1f),

            style = MaterialTheme.typography.titleLarge,

            color = Color.Red
        )
        Button(onClick = onClick, modifier = Modifier.padding(8.dp)) {
            Text(text = "Try Again!")
        }

    }


}