package com.example.spaceinvader

import android.graphics.RectF

class Bullet {
    var x = 0
    var y = 0
    var rect = RectF()
    var isActive = false

    init{
        rect.set(0f, 0f, 12f, 24f)
    }
}