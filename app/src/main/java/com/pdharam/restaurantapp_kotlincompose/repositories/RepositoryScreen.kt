package com.pdharam.restaurantapp_kotlincompose.repositories

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

@Composable
fun RepositoryScreen(
    repo: LazyPagingItems<Repository>,
    timerText: String,
    getTimer: () -> CustomCountDown,
    onPauseTimer: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(
            vertical = 8.dp,
            horizontal = 8.dp
        )
    ) {

        item {
            CountDownItem(timerText = timerText, getTimer = getTimer, onPauseTimer = onPauseTimer)
        }

        items(count = repo.itemCount) { index ->
            val item = repo[index]
            if (item != null)
                RepositoryItem(index = index, repo = item)
        }

        val refreshState = repo.loadState.refresh
        when {
            refreshState is LoadState.Loading -> {
                item { LoadingItem(modifier = Modifier.fillParentMaxSize()) }
            }

            refreshState is LoadState.Error -> {
                val error = refreshState.error

                item {
                    ErrorItem(
                        message = error.localizedMessage ?: "",
                        modifier = Modifier.fillParentMaxSize(), onClick = {
                            repo.retry()
                        })
                }
            }
        }

        val appendLoadState = repo.loadState.append

        when {
            appendLoadState is LoadState.Loading -> {
                item { LoadingItem(modifier = Modifier.fillMaxWidth()) }
            }

            appendLoadState is LoadState.Error -> {
                val error = appendLoadState.error

                item {
                    ErrorItem(
                        message = error.localizedMessage ?: "",
                        modifier = Modifier.fillParentMaxSize(), onClick = {
                            repo.retry()
                        })
                }
            }
        }
    }

}

@Composable
fun MailButton(mailId: Int, onMailClick: (Int) -> Unit) {
    Button(onClick = { onMailClick(mailId) }) {
        Text(text = "Open Mail $mailId")
    }
}