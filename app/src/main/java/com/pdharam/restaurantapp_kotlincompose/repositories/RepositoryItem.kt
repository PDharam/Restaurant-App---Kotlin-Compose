package com.pdharam.restaurantapp_kotlincompose.repositories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun RepositoryItem(index: Int, repo: Repository) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .padding(8.dp)
            .height(120.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = index.toString(),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .weight(0.2f)
                    .padding(8.dp)
            )
            Column(modifier = Modifier.weight(0.8f)) {
                Text(text = repo.name, style = MaterialTheme.typography.titleLarge)
                Text(
                    text = repo.description, style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis, maxLines = 3
                )
            }
        }

    }
}