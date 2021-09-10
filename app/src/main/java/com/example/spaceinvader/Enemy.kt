package com.example.spaceinvader

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class Enemy (context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val paint : Paint = Paint()

    override fun onDraw(canvas: Canvas) {
        //super.onDraw(canvas)
        val u = width.toFloat()/12

        val path : Path = Path()

        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true

        path.moveTo(0f, u)
        path.lineTo(u, u)
        path.lineTo(u, u*3)
        path.lineTo(u*2, u*3)
        path.lineTo(u*2, u*2)
        path.lineTo(u*3, u*2)
        path.lineTo(u*3, u)
        path.lineTo(u*2, u)
        path.lineTo(u*2, 0f)
        path.lineTo(u*3, 0f)
        path.lineTo(u*3, u)
        path.lineTo(u*4, u)
        path.lineTo(u*4, u*2)
        path.lineTo(u*7, u*2)
        path.lineTo(u*7, u)
        path.lineTo(u*8, u)
        path.lineTo(u*8, 0f)
        path.lineTo(u*9, 0f)
        path.lineTo(u*9, u)
        path.lineTo(u*8, u)
        path.lineTo(u*8, u*2)
        path.lineTo(u*9, u*2)
        path.lineTo(u*9, u*3)
        path.lineTo(u*10, u*3)
        path.lineTo(u*10, u)
        path.lineTo(u*11, u)
        path.lineTo(u*11, u*5)
        path.lineTo(u*10, u*5)
        path.lineTo(u*10, u*6)
        path.lineTo(u*9, u*6)
        path.lineTo(u*9, u*7)
        path.lineTo(u*10, u*7)
        path.lineTo(u*10, u*8)
        path.lineTo(u*9, u*8)
        path.lineTo(u*9, u*7)
        path.lineTo(u*8, u*7)
        path.lineTo(u*8, u*6)
        path.lineTo(u*3, u*6)
        path.lineTo(u*3, u*7)
        path.lineTo(u*2, u*7)
        path.lineTo(u*2, u*8)
        path.lineTo(u, u*8)
        path.lineTo(u, u*7)
        path.lineTo(u*2, u*7)
        path.lineTo(u*2, u*6)
        path.lineTo(u, u*6)
        path.lineTo(u, u*5)
        path.lineTo(u, u*5)
        path.lineTo(0f, u*5)
        path.close()

        canvas.drawPath(path, paint)
    }
}