package com.example.spaceinvader

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class PlayableCharacter (context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val paint : Paint = Paint()

    override fun onDraw(canvas: Canvas) {
        //super.onDraw(canvas)
        val w1 = width.toFloat()
        val h1 = height.toFloat()

        val path : Path = Path()

        paint.color = Color.DKGRAY
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true

        path.moveTo(w1/2, 0f)
        path.lineTo(w1, h1/2)
        path.lineTo(w1, h1)
        path.lineTo(0f, h1)
        path.lineTo(0f, h1/2)
        path.lineTo(w1/2, 0f)
        path.close()

        canvas.drawPath(path, paint)
    }
}