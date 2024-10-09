package com.pdharam.restaurantapp_kotlincompose.repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow

class RepositoryViewModel(private val repositoryPagingSource: RepositoryPagingSource = RepositoryPagingSource()) :
    ViewModel() {

    val repositoriesFlow: Flow<PagingData<Repository>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { repositoryPagingSource })
        .flow.cachedIn(viewModelScope)


}