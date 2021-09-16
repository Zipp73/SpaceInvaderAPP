package com.example.spaceinvader

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.MotionEvent
import android.view.SurfaceView
import android.widget.Toast

class GameView(context: Context, screenX: Int, screenY: Int) : SurfaceView(context), SensorEventListener, Runnable{
    private lateinit var gameThread : Thread
    private var isPlaying = false
    private var screenX = 0
    private var screenY = 0
    private var paint : Paint
    companion object{
        var screenRatioX = 0f
        var screenRatioY = 0f
    }
    private var pc : PlayableCharacter
    private lateinit var sensorManager : SensorManager
    var control = 0
    val MAX_FRAME_TIME = 1000/60

    init{
        this.screenX = screenX
        screenRatioX = 1920f / screenX
        this.screenY = screenY
        screenRatioY = 1080f / screenY
        pc = PlayableCharacter(this.screenX, resources)
        pc.y = this.screenY

        paint = Paint()
        paint.color = Color.WHITE
        paint.isAntiAlias = true
    }

    override fun run() {
        while (isPlaying){
            update()
            render()
            sleep()
            control++
        }
    }

    private fun sleep(){
        Thread.sleep(MAX_FRAME_TIME.toLong())
    }

    private fun update(){
        setUpSensorStuff()
        if(pc.x < 0) pc.x = 0
        if(pc.x > screenX-pc.width) pc.x = screenX - pc.width

        pc.bullets.forEach{
            if(it.rect.top < -24) it.isActive = false
            if(it.isActive) it.rect.set(it.rect.left, it.rect.top - 10 * screenRatioY, it.rect.right, it.rect.bottom - 10 * screenRatioY)
        }
    }

    private fun render(){
        if(holder.surface.isValid){
            val canvas : Canvas = holder.lockCanvas()

            canvas.drawARGB(255,0,0,0)

            paint.color = Color.WHITE
            try{
                canvas.drawBitmap(pc.b, pc.x.toFloat(), pc.y.toFloat(), paint)

                for (bullet: Bullet in pc.bullets){
                    /*if(bullet.isActive)*/ canvas.drawRect(bullet.rect, paint)
                }
            }finally {
                holder.unlockCanvasAndPost(canvas)
            }
        }
    }

    fun resume(){
        isPlaying = true
        gameThread = Thread(this)
        gameThread.start()
    }

    fun pause(){
        isPlaying = false
        gameThread.join()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                /*pc.isStopped = false
                if(event.x < screenX/2){ pc.isGoingLeft = true }
                if(event.x > screenX/2){ pc.isGoingLeft = false }
                Toast.makeText(context, "OOOOOO" + event.x + " : " + screenX/2 + " ; " + control, Toast.LENGTH_LONG).show()*/
            }
            MotionEvent.ACTION_UP -> {
                /*pc.isStopped = true
                //if(event.x < screenX/2){ pc.isGoingLeft = true }*/
                Toast.makeText(context, "aaaaaaa" + control, Toast.LENGTH_LONG).show()
                //shoot()
                if(!pc.bullets[pc.nextShot].isActive){
                    pc.bullets[pc.nextShot].rect.set(pc.x + pc.width/2 - 6f, pc.y - 12f, pc.x + pc.width/2 + 6f, pc.y + 12f)
                    pc.bullets[pc.nextShot].isActive = true
                    if (pc.nextShot == 4) pc.nextShot = 0
                    else pc.nextShot++
                }
            }
        }
        return true
    }

    private fun setUpSensorStuff(){
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event?.sensor?.type == Sensor.TYPE_GYROSCOPE) {
            pc.x += (event.values[1] * 4 * screenRatioX).toInt()
            //pc.bullets[1]. x += (event.values[1] * 4 * screenRatioX).toInt()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    fun shoot(){
        for (bullet: Bullet in pc.bullets){
            bullet.x = pc.x + (pc.width / 2)
            bullet.y = pc.y + 2*pc.height
            //bullet.isActive = true
        }
    }
}