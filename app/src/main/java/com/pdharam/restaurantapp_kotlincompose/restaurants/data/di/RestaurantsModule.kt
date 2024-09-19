package com.pdharam.restaurantapp_kotlincompose.restaurants.data.di

import android.content.Context
import androidx.room.Room
import com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.local.RestaurantDao
import com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.local.RestaurantDb
import com.pdharam.restaurantapp_kotlincompose.restaurants.data.data_source.remote.RestaurantsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RestaurantsModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://fir-project-s-default-rtdb.firebaseio.com/")
            .build()
    }

    @Provides
    fun provideRestaurantApi(retrofit: Retrofit): RestaurantsApiService {
        return retrofit.create(RestaurantsApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext appContext: Context): RestaurantDb {
        return Room.databaseBuilder(
            appContext,
            RestaurantDb::class.java,
            "restaurant_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideRestaurantDao(restaurantDb: RestaurantDb): RestaurantDao = restaurantDb.restaurantDao
}