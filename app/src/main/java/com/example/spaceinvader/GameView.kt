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
    private lateinit  var enemies : List<Enemy>
    private lateinit var sensorManager : SensorManager
    var control = 0
    val MAX_FRAME_TIME = 1000/60
    var isGameOver = false

    init{
        this.screenX = screenX
        screenRatioX = 1920f / screenX
        this.screenY = screenY
        screenRatioY = 1080f / screenY
        pc = PlayableCharacter(this.screenX, resources)
        pc.y = this.screenY
        enemies = listOf(Enemy(resources), Enemy(resources), Enemy(resources), Enemy(resources))
        enemies.forEach {
            it.y = 0
        }

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
            if(it.isActive) it.rect.set(it.rect.left, (it.rect.top - 10 * screenRatioY).toInt(), it.rect.right, (it.rect.bottom - 10 * screenRatioY).toInt()
            )
            for(e: Enemy in enemies) {
                if(Rect.intersects(e.getCollisionShape(), it.getCollisionShape())) {
                    e.y = -100 -e.height
                    it.rect.set(0, -100, it.width.toInt(), (-100+it.height).toInt())
                    e.isAlive = false
                }
            }
        }

        enemies.forEach{
            if(it.isAlive){
                if (it.isGoingLeft) it.x -= 10 * screenRatioX.toInt()
                if (!it.isGoingLeft) it.x += 10 * screenRatioX.toInt()
                if (it.x > screenX - it.width) {
                    it.isGoingLeft = true
                    it.y += (it.height * screenRatioY + 20).toInt()
                }
                if (it.x < 0) {
                    it.isGoingLeft = false
                    it.y += (it.height * screenRatioY + 20).toInt()
                }
                if (it.y > screenY - pc.width) {
                    isGameOver = true
                }
            }
        }
    }

    private fun render(){
        if(holder.surface.isValid){
            val canvas : Canvas = holder.lockCanvas()

            canvas.drawARGB(255,0,0,0)

            try{
                if(isGameOver){
                    isPlaying = false
                    paint.color = Color.RED
                    canvas.drawRect(pc.dead, paint)
                    return
                }

                paint.color = Color.WHITE
                canvas.drawBitmap(pc.b, pc.x.toFloat(), pc.y.toFloat(), paint)

                for (bullet: Bullet in pc.bullets){
                    if(!bullet.isActive) canvas.drawRect(Rect(-112, -124, -100, -100), paint)
                    if(bullet.isActive) {
                        paint.color = Color.WHITE
                        paint.style = Paint.Style.FILL
                        canvas.drawRect(bullet.rect, paint)
                        paint.color = Color.RED
                        paint.style = Paint.Style.STROKE
                        paint.strokeWidth = 1f
                        canvas.drawRect(bullet.getCollisionShape(), paint)
                    }
                }

                if(!enemies[0].isAlive) canvas.drawBitmap(enemies[0].getInvader(), -200f, -400f, paint)
                if(enemies[0].isAlive){
                    canvas.drawBitmap(
                        enemies[0].getInvader(),
                        enemies[0].x.toFloat(),
                        enemies[0].y.toFloat(),
                        paint
                    )
                    paint.color = Color.RED
                    paint.style = Paint.Style.STROKE
                    paint.strokeWidth = 1f
                    canvas.drawRect(enemies[0].getCollisionShape(), paint)
                }
                //enemies[0].isAlive = true
                /*enemies.forEach {
                    canvas.drawBitmap(it.getInvader(), it.x.toFloat(), it.y.toFloat(), paint)
                    it.isAlive = true
                }*/
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
                //Toast.makeText(context, "aaaaaaa" + enemies[0].x.toFloat() + " " + enemies[0].y.toFloat() + "  " + enemies[0].b1.width + " " + enemies[0].b1.height, Toast.LENGTH_LONG).show()
                //shoot()
                if(pc.nextShot == -1) {
                    enemies[0].isAlive = true
                    pc.nextShot++
                }else
                if(!pc.bullets[pc.nextShot].isActive && pc.nextShot >= 0){
                    pc.bullets[pc.nextShot].rect.set(pc.x + pc.width/2, pc.y,
                        (pc.x + pc.width/2 + pc.bullets[pc.nextShot].width).toInt(), (pc.y + pc.bullets[pc.nextShot].height).toInt()
                    )
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