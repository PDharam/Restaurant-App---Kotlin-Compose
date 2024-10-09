package com.pdharam.restaurantapp_kotlincompose.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DependencyContainer {

    val repositoryRetrofitClient: RepositoryApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.github.com/search/")
        .build().create(RepositoryApiService::class.java)
}