package com.example.spaceinvader

import android.graphics.Rect
import android.graphics.RectF

class Bullet {
    var x = 0
    var y = 0
    var rect = Rect()
    var isActive = false
    var width = 12f
    var height = 24f

    init{
        rect.set(0, 0, width.toInt(), height.toInt())
    }

    fun getCollisionShape(): Rect {
        return rect
    }
}