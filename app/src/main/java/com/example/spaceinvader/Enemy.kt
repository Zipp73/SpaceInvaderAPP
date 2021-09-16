package com.example.spaceinvader

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import java.util.*

class Enemy(res: Resources) {
    var x = 0
    var y = 0
    var b1 : Bitmap
    var b2 : Bitmap
    var width = 0
    var height = 0
    var invaderCounter = 1
    var isAlive = false
    var isGoingLeft = false
    private val ran : Random = Random()

    init {
        b1 = BitmapFactory.decodeResource(res, R.drawable.invader1)
        b2 = BitmapFactory.decodeResource(res, R.drawable.invader2)

        width = b1.width
        width /= 6
        width = (width * GameView.screenRatioX).toInt()

        height = b1.height
        height /= 3
        height = (height * GameView.screenRatioY).toInt()

        b1 = Bitmap.createScaledBitmap(b1, width.toInt(), height.toInt(), false)
        b2 = Bitmap.createScaledBitmap(b2, width.toInt(), height.toInt(), false)

        x = -width.toInt()
    }

    fun getInvader(): Bitmap{
        if(invaderCounter == 1){
            invaderCounter++
            return b1
        }
        invaderCounter--
        return b2
    }

    fun getCollisionShape(): Rect{
        return Rect(x, y, (x + width).toInt(), (y + height).toInt())
    }

    fun takeAim(plPos : Float, plLenght : Float): Boolean{
        var n = -1
        if(plLenght + plPos > x && plLenght + plPos < x || (plPos > x && plPos < x + width)){
            n = ran.nextInt(10)
            if(n == 0) return true
        }
        n = ran.nextInt(100)
        if (n == 0) return true
        return false
    }
}