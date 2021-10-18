package com.example.spaceinvader

import android.content.Context
import android.graphics.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.TypedValue
import android.view.MotionEvent
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class GameView(context: Context, screenX: Int, screenY: Int) : SurfaceView(context), SensorEventListener, Runnable{
    private lateinit var gameThread : Thread
    private var isPlaying = false
    private var screenX = 0
    private var screenY = 0
    private var paint : Paint
    companion object{
        var screenRatioX = 0f
        var screenRatioY = 0f
        var win = false
    }
    private var pc : PlayableCharacter
    private var enemies : List<Enemy>
    private lateinit var sensorManager : SensorManager
    var control = 0
    val MAX_FRAME_TIME = 1000/60
    var isGameOver = false
    private var enemBullets : MutableList<Bullet> = mutableListOf()
    private val maxEnemBullets = 30
    private val enemyXPos : FloatArray
    private var score = 0
    private var enemyAlive = 4


    init{
        this.screenX = screenX
        screenRatioX = 1920f / screenX
        this.screenY = screenY
        screenRatioY = 1080f / screenY
        pc = PlayableCharacter(this.screenX, resources)
        pc.y = this.screenY.toFloat()
        enemies = listOf(Enemy(resources), Enemy(resources), Enemy(resources), Enemy(resources))
        var i = 0
        while(i < maxEnemBullets){
            enemBullets.add(Bullet())
            i++
        }
        enemies.forEach {
            it.y = 0f
        }

        enemyXPos = FloatArray(enemies.size)
        val pos = (screenX - 48f)/4
        i = 0
        while (i < enemyXPos.size) {
            enemyXPos[i] = 48 + i * pos
            enemies[i].x = enemyXPos[i]
            i++
        }

        paint = Paint()
        paint.color = Color.WHITE
        paint.isAntiAlias = true

        Enemy.speed = 10
        win = false
    }

    override fun run() {
        while (isPlaying){
            update()
            render()
            sleep()
            control++
        }
        if(isGameOver) onGameOver()
    }

    private fun sleep(){
        Thread.sleep(MAX_FRAME_TIME.toLong())
    }

    private fun update(){
        setUpSensorMovement()
        if(pc.x < 0) pc.x = 0f
        if(pc.x > screenX-pc.width) pc.x = screenX.toFloat() - pc.width

        pc.bullets.forEach{
            if(it.rect.top < -124) it.isActive = false
            if(it.isActive) it.rect.set(it.rect.left, (it.rect.top - 15 * screenRatioY), it.rect.right, (it.rect.bottom - 15 * screenRatioY))
            for(e: Enemy in enemies) {
                if(RectF.intersects(e.getCollisionShape(), it.getCollisionShape())) {
                    score += Enemy.speed
                    e.x = -100f -e.width.toFloat()
                    it.rect.set(0f, -100f, it.width, (-100f+it.height))
                    e.isAlive = false
                    Enemy.speed++
                    enemyAlive--
                    GameActivity.score = score
                }
            }
        }

        enemies.forEach{
            if(it.isAlive){
                if(it.takeAim(pc.x, pc.width.toFloat())) enemShoot(it.x, it.y)
                if (it.isGoingLeft) it.x -= Enemy.speed * screenRatioX.toInt()
                if (!it.isGoingLeft) it.x += Enemy.speed * screenRatioX.toInt()
                if (it.x > screenX - it.width) {
                    it.isGoingLeft = true
                    it.y += (it.height + 8)
                }
                if (it.x < 0) {
                    it.isGoingLeft = false
                    it.y += (it.height + 8)
                }
                if (it.y > screenY - pc.width) {
                    isGameOver = true
                }
            }
        }

        enemBullets.forEach{
            if(it.rect.top > screenY + it.height + 100f) it.isActive = false
            if(it.isActive) it.rect.set(it.rect.left, (it.rect.top + 10 * screenRatioY), it.rect.right, (it.rect.bottom + 10 * screenRatioY))
            if(RectF.intersects(pc.getCollisionShape(), it.getCollisionShape())) isGameOver = true
        }

        if(enemyAlive == 0) {
            isGameOver = true
            win = true
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
                    //canvas.drawRect(pc.dead, paint)
                    return
                }

                paint.color = Color.WHITE
                canvas.drawBitmap(pc.b, pc.x, pc.y, paint)

                for (bullet: Bullet in pc.bullets){
                    if(!bullet.isActive) canvas.drawRect(Rect(-112, -124, -100, -100), paint)
                    if(bullet.isActive) {
                        paint.color = Color.WHITE
                        paint.style = Paint.Style.FILL
                        canvas.drawRect(bullet.rect, paint)
                    }
                }

                var i = 0
                while(i < enemies.size){
                    if (!enemies[i].isAlive) canvas.drawBitmap(enemies[i].getInvader(), -200f, -400f, paint)
                    if (enemies[i].isAlive) {
                        //enemies[i].x = enemyXPos[i]
                        canvas.drawBitmap(enemies[i].getInvader(), enemies[i].x, enemies[i].y, paint)
                    }
                    i++
                }

                for (bullet: Bullet in enemBullets){
                    if(!bullet.isActive) canvas.drawRect(Rect(-112, -124, -100, -100), paint)
                    if(bullet.isActive) {
                        paint.color = getProperColor()
                        paint.style = Paint.Style.FILL
                        canvas.drawRect(bullet.rect, paint)
                    }
                }
            }finally {
                holder.unlockCanvasAndPost(canvas)
            }
        }
    }

    private fun getProperColor(): Int{
        val t = TypedValue()
        context.theme.resolveAttribute(R.attr.colorButtonNormal, t, true)
        return t.data
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
            MotionEvent.ACTION_DOWN -> { }
            MotionEvent.ACTION_UP -> {
                if(pc.nextShot == -1) {
                    enemies.forEach { it.isAlive = true }
                    pc.nextShot++
                }else
                    if(!pc.bullets[pc.nextShot].isActive && pc.nextShot >= 0){
                        pc.bullets[pc.nextShot].rect.set(pc.x + pc.width/2, pc.y,
                            (pc.x + pc.width/2 + pc.bullets[pc.nextShot].width), (pc.y + pc.bullets[pc.nextShot].height))
                        pc.bullets[pc.nextShot].isActive = true
                        if (pc.nextShot == 4) pc.nextShot = 0
                        else pc.nextShot++
                    }
            }
        }
        return true
    }

    private fun setUpSensorMovement(){
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event?.sensor?.type == Sensor.TYPE_GYROSCOPE) {
            pc.apply{
                x += (event.values[1] * 4 * MainActivity.densityPixelFactor).toInt()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    fun enemShoot(posX : Float, posY : Float){
        var i = 0
        do {
            if(!enemBullets[i].isActive) {
                enemBullets[i].rect.set(posX + enemies[0].width/2, posY, (posX + enemies[0].width/2 + enemBullets[i].width), (posY + enemBullets[i].height))
                enemBullets[i].isActive = true
                return
            }
        }while(enemBullets[i++].isActive)
    }

    private fun onGameOver(){
        val goFrag : Fragment = GameOverFragment()
        val activity : AppCompatActivity = context as AppCompatActivity
        val fm : FragmentManager = activity.supportFragmentManager
        val t : FragmentTransaction = fm.beginTransaction()
        t.add(R.id.container, goFrag, goFrag.tag)
        t.commit()
    }
}