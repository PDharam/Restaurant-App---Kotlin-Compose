package com.pdharam.restaurantapp_kotlincompose.repositories

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun CountDownItem(timerText: String, getTimer: () -> CustomCountDown, onPauseTimer: () -> Unit) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(getTimer())
        onDispose {
            onPauseTimer()
            lifecycleOwner.lifecycle.removeObserver(getTimer())
        }
    }
    Text(text = timerText)
}