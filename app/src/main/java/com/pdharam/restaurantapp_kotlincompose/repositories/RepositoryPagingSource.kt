package com.pdharam.restaurantapp_kotlincompose.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState

class RepositoryPagingSource(private val restInterface: RepositoryApiService = DependencyContainer.repositoryRetrofitClient) :
    PagingSource<Int, Repository>() {
    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        try {
            val nextPage = params.key ?: 1
            val repo = restInterface.getRepository(nextPage).repos

            return LoadResult.Page(
                data = repo,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}