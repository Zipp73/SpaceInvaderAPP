package com.example.spaceinvader

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF
import java.util.*

class Enemy(res: Resources, screenX: Int) {
    var x = 0f
    var y = 0f
    var b1: Bitmap
    var b2: Bitmap
    var width = 0
    var height = 0
    var invaderCounter = 1
    var isAlive = false
    private val ran: Random = Random()

    init {
        b1 = BitmapFactory.decodeResource(res, R.drawable.invader1)
        b2 = BitmapFactory.decodeResource(res, R.drawable.invader2)

        width = (screenX - 240)/8
        height = width / 2

        b1 = Bitmap.createScaledBitmap(b1, width, height, false)
        b2 = Bitmap.createScaledBitmap(b2, width, height, false)
    }

    fun getInvader(): Bitmap {
        return if (invaderCounter++ % 20 > 10) b1
        else b2
    }

    fun getCollisionShape(): RectF {
        return RectF(x, y, (x + width), (y + height))
    }

    fun takeAim(plPos: Float, plLenght: Float): Boolean {
        var n: Int
        if (plLenght + plPos > x && plLenght + plPos < x || (plPos > x && plPos < x + width)) {
            n = ran.nextInt(200)
            if (n == 0) return true
        }
        n = ran.nextInt(2000)
        if (n == 0) return true
        return false
    }
}