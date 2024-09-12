package com.pdharam.restaurantapp_kotlincompose

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile

@Database(version = 2, entities = [Restaurant::class], exportSchema = false)
abstract class RestaurantDb : RoomDatabase() {
    abstract val restaurantDao: RestaurantDao

    companion object {
        @Volatile
        private var INSTANCE: RestaurantDao? = null

        fun getDaoInstance(context: Context): RestaurantDao {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = buildDatabase(context).restaurantDao
                    INSTANCE = instance
                }
                return instance
            }
        }

        private fun buildDatabase(context: Context):
                RestaurantDb = Room.databaseBuilder(
            context.applicationContext,
            RestaurantDb::class.java,
            "restaurant_db"
        ).fallbackToDestructiveMigration()
            .build()

    }

}