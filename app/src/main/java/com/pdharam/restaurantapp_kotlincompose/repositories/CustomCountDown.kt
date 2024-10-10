package com.pdharam.restaurantapp_kotlincompose.repositories

import android.os.CountDownTimer
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class CustomCountDown(
    private val onTick: (currentValue: Int) -> Unit,
    private val onFinish: () -> Unit
) : DefaultLifecycleObserver {

    var timer: InternalTimer = InternalTimer(onTick = onTick, onFinish = onFinish, 60000, 1000)
        private set

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        if (timer.lastKnownTime > 0) {
            timer.cancel()
            timer = InternalTimer(
                onTick = onTick,
                onFinish = onFinish,
                millisInFuture = timer.lastKnownTime,
                1000
            )
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        stop()
    }

    fun stop() {
        timer.cancel()
    }


    class InternalTimer(
        private val onTick: (currentValue: Int) -> Unit,
        private val onFinish: () -> Unit,
        millisInFuture: Long,
        countDownInterval: Long
    ) : CountDownTimer(millisInFuture, countDownInterval) {
        var lastKnownTime = millisInFuture

        init {
            this.start()
        }

        override fun onTick(millisUntilFinished: Long) {
            lastKnownTime = millisUntilFinished
            onTick(millisUntilFinished.toInt())
        }

        override fun onFinish() {
            onFinish.invoke()
            lastKnownTime = 0
        }

    }
}