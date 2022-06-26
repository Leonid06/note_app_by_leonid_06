package com.example.leonidsnotesapplication.presentation.notes_feature.callbacks

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs


open class OnTouchListener(val context : Context?) : View.OnTouchListener{

    companion object {
        private const val SwipeThreshold = 100
        private const val SwipeVelocityThreshold = 100
    }

    private val gestureDetector = GestureDetector(context, OnGestureListener())

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    fun onSwipeRight(): Boolean { return false }
    open fun onClick() : Boolean {return false}
    open fun onSwipeLeft(): Boolean { return false }
    fun onSwipeUp(): Boolean { return false }
    fun onSwipeDown(): Boolean { return false }

    private inner class OnGestureListener : GestureDetector.SimpleOnGestureListener(){
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return onClick()
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float,
        ): Boolean {
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x

                if (abs(diffX) > abs(diffY)) {
                    if (abs(diffX) > SwipeThreshold && abs(velocityX) > SwipeVelocityThreshold) {
                        return when {
                            diffX > 0 -> onSwipeRight()
                            else -> onSwipeLeft()
                        }
                    }
                } else if (abs(diffY) > SwipeThreshold && abs(velocityY) > SwipeVelocityThreshold) {
                    return when {
                        diffY > 0 -> onSwipeDown()
                        else -> onSwipeUp()
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return false
        }
    }
}