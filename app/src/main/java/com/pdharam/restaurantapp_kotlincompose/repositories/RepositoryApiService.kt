package com.pdharam.restaurantapp_kotlincompose.repositories

import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoryApiService {

    @GET("repositories?q=mobile&sort=stars&per_page=20")
    suspend fun getRepository(@Query("page") page: Int): RepositoriesResponse
}