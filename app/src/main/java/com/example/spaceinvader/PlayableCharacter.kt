package com.example.spaceinvader

import android.content.res.Resources
import android.graphics.*

class PlayableCharacter(screenX: Int, res: Resources) {
    var x = 0f
    var y = 0f
    var width = 0
    var height = 0
    var b : Bitmap = BitmapFactory.decodeResource(res, R.drawable.playership)
    var nextShot = -1
    var bullets : List<Bullet> = listOf(Bullet(), Bullet(), Bullet(), Bullet(), Bullet())
    var dead : RectF

    init {
        width = b.width
        width /= 3
        width = (width * GameView.screenRatioX).toInt()
        height = b.height
        height /= 1
        height = (height * GameView.screenRatioY).toInt()
        b = Bitmap.createScaledBitmap(b, width, height, false)
        x = screenX / 2f
        y = 64f * GameView.screenRatioY.toInt()

        dead = RectF(x, y, x + width, y + height)
    }

    fun getCollisionShape(): RectF{
        return RectF(x, y, x + width, y + height)
    }
}