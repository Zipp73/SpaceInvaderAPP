package com.example.spaceinvader

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class PlayableCharacter(screenX: Int, res: Resources) {
    var x = 0
    var y = 0
    var width = 0
    var height = 0
    var b : Bitmap = BitmapFactory.decodeResource(res, R.drawable.playership)
    var nextShot = 0
    var bullets : List<Bullet> = listOf<Bullet>(Bullet(), Bullet(), Bullet(), Bullet(), Bullet())

    init {
        width = b.width
        width /= 4
        width *= GameView.screenRatioX.toInt()
        height = b.height
        height /= 4
        height *= GameView.screenRatioY.toInt()
        //b = Bitmap.createScaledBitmap(b, width, height, false)
        x = screenX / 2
        y = 64 * GameView.screenRatioY.toInt()


    }

    /*fun getShoot(){
        if(toShoot != 0){
            if(shootCounter == 1) shootCounter++
            if(shootCounter == 2) shootCounter++
            if(shootCounter == 3) shootCounter++
            if(shootCounter == 4) shootCounter++
        }
    }*/

    /*fun toPath(){
        val w1 = width.toFloat()
        val h1 = height.toFloat()

        path.moveTo(w1/2, 0f)
        path.lineTo(w1, h1/2)
        path.lineTo(w1, h1)
        path.lineTo(0f, h1)
        path.lineTo(0f, h1/2)
        path.lineTo(w1/2, 0f)
        path.close()
    }*/
}