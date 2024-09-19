package com.pdharam.restaurantapp_kotlincompose

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RestaurantApplication : Application() {
    init {
        app = this
    }

    companion object {
        private lateinit var app: RestaurantApplication
        fun getAppContext(): Context = app.applicationContext
    }
}