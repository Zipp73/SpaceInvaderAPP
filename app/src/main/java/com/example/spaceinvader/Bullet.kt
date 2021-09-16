package com.example.spaceinvader

import android.graphics.RectF

class Bullet {
    var x = 0
    var y = 0
    var rect = RectF()
    var isActive = false
    var width = 12f
    var height = 24f

    init{
        rect.set(0f, 0f, width, height)
    }

    fun getCollisionShape(): RectF {
        return rect
    }
}