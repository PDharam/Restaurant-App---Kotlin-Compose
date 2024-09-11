package com.pdharam.restaurantapp_kotlincompose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class RestaurantViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {

    val state = mutableStateOf(dummyRestaurants.restoreSelection())


    fun toggleFavourite(id: Int) {
        //take mutable list
        val restaurantList = state.value.toMutableList()
        //find index based on id property
        val itemIndex = restaurantList.indexOfFirst { it.id == id }
        //take particular item using received index
        val item = restaurantList[itemIndex]
        //update item using item.copy
        restaurantList[itemIndex] = item.copy(isFavourite = !item.isFavourite)
        storeSelection(restaurantList[itemIndex])
        //finally update the state
        state.value = restaurantList
    }

    private fun storeSelection(item: Restaurant) {
        //Take list of id's if list not available return  empty list - type mutable so we can update it.
        val savedToggled = stateHandle.get<List<Int>>(FAVOURITE).orEmpty().toMutableList()
        //If item is favourite then add into savedToggled list otherwise removed it from list.
        if (item.isFavourite) savedToggled.add(item.id) else savedToggled.remove(item.id)
        //Set updated list again in stateHandle obj.
        stateHandle[FAVOURITE] = savedToggled
    }

    private fun List<Restaurant>.restoreSelection(): List<Restaurant> {
        stateHandle.get<List<Int>>(FAVOURITE)?.let { selectedIds ->
            val restaurantMap = this.associateBy { it.id }

            selectedIds.forEach { id ->
                restaurantMap[id]?.isFavourite = true
            }

            return restaurantMap.values.toList()

        }

        return this
    }

    companion object {
        const val FAVOURITE = "favourite"
    }
}