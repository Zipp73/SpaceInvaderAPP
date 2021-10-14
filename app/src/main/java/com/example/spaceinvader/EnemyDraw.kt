package com.example.spaceinvader

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

class EnemyDraw (context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val paint : Paint = Paint()
    private var path : Path = Path()

    private fun getProperColor(): Int{
        val t = TypedValue()
        context.theme.resolveAttribute(R.attr.colorButtonNormal, t, true)
        return t.data
    }

    override fun onDraw(canvas: Canvas) {
        //super.onDraw(canvas)
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        paint.textSize = width.toFloat()/8

        toPath()
        path.close()
        canvas.drawPath(path, paint)

        //path.reset()
        paint.color = getProperColor()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 6f
        canvas.drawPath(path, paint)

        paint.style = Paint.Style.FILL
        canvas.drawText("Space  Invader", width.toFloat()/11, width.toFloat()/22*9,paint)
    }

    fun toPath(){
        val u = width.toFloat()/11

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
    }
}