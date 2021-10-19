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
    private var enemiesRow1 : List<Enemy>
    private var enemiesRow2 : List<Enemy>
    private lateinit var sensorManager : SensorManager
    val MAX_FRAME_TIME = 1000/60
    var isGameOver = false
    private var enemBullets : MutableList<Bullet> = mutableListOf()
    private val maxEnemBullets = 30
    private val enemyXPos : FloatArray
    private var score = 0
    private var enemyAlive = -1
    private var enemySpeed = 0
    private var enemyGoingLeft = false

    init{
        this.screenX = screenX
        screenRatioX = 1920f / screenX
        this.screenY = screenY
        screenRatioY = 1080f / screenY
        pc = PlayableCharacter(this.screenX, resources)
        pc.y = this.screenY.toFloat()
        enemiesRow1 = listOf(Enemy(resources), Enemy(resources), Enemy(resources), Enemy(resources),
            Enemy(resources), Enemy(resources), Enemy(resources), Enemy(resources))
        enemiesRow2 = listOf(Enemy(resources), Enemy(resources), Enemy(resources), Enemy(resources),
            Enemy(resources), Enemy(resources), Enemy(resources), Enemy(resources))
        var i = 0
        while(i < maxEnemBullets){
            enemBullets.add(Bullet())
            i++
        }
        enemiesRow1.forEach {
            it.y = 0f
        }
        enemiesRow2.forEach {
            it.y = (it.height + 8).toFloat()
        }

        enemyXPos = FloatArray(enemiesRow1.size)
        val pos = (screenX - 48f)/((screenX - 192f)/enemiesRow1[0].width)
        i = 0
        while (i < enemyXPos.size) {
            enemyXPos[i] = 48 + (i * pos)
            enemiesRow1[i].x = enemyXPos[i]
            enemiesRow2[i].x = enemyXPos[i]
            i++
        }

        paint = Paint()
        paint.color = Color.WHITE
        paint.isAntiAlias = true

        enemySpeed = 0
        enemyAlive = enemiesRow1.size + enemiesRow2.size
        win = false
    }

    override fun run() {
        while (isPlaying){
            update()
            render()
            sleep()
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
            for(e: Enemy in enemiesRow1) {
                if(RectF.intersects(e.getCollisionShape(), it.getCollisionShape())) {
                    score += (enemySpeed + 1)
                    e.x = -100f -e.width.toFloat()
                    it.rect.set(0f, -100f, it.width, (-100f+it.height))
                    e.isAlive = false
                    enemySpeed++
                    enemyAlive--
                    GameActivity.score = score
                }
            }
            for(e: Enemy in enemiesRow2) {
                if(RectF.intersects(e.getCollisionShape(), it.getCollisionShape())) {
                    score += (enemySpeed + 1)
                    e.x = -100f -e.width.toFloat()
                    it.rect.set(0f, -100f, it.width, (-100f+it.height))
                    e.isAlive = false
                    enemySpeed++
                    enemyAlive--
                    GameActivity.score = score
                }
            }
        }

        enemiesRow1.forEach{
            if(it.isAlive){
                if(it.takeAim(pc.x, pc.width.toFloat())) enemShoot(it.x, it.y)
                if (enemyGoingLeft) it.x -= enemySpeed * screenRatioX.toInt()
                if (!enemyGoingLeft) it.x += enemySpeed * screenRatioX.toInt()
                if (it.x > screenX - it.width) {
                    enemyGoingLeft = true
                    enemiesGoingDown()
                }
                if (it.x < 0) {
                    enemyGoingLeft = false
                    enemiesGoingDown()
                }
                if (it.y > screenY - pc.width) {
                    isGameOver = true
                }
            }
        }

        enemiesRow2.forEach{
            if(it.isAlive){
                if(it.takeAim(pc.x, pc.width.toFloat())) enemShoot(it.x, it.y)
                if (enemyGoingLeft) it.x -= enemySpeed * screenRatioX.toInt()
                if (!enemyGoingLeft) it.x += enemySpeed * screenRatioX.toInt()
                if (it.x > screenX - it.width) {
                    enemyGoingLeft = true
                    //it.y += (it.height + 8)
                    enemiesGoingDown()
                }
                if (it.x < 0) {
                    enemyGoingLeft = false
                    //it.y += (it.height + 8)
                    enemiesGoingDown()
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

    private fun enemiesGoingDown(){
        enemiesRow1.forEach {
            it.y += (it.height + 8)
        }
        enemiesRow2.forEach {
            it.y += (it.height + 8)
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
                while(i < enemiesRow1.size){
                    if (!enemiesRow1[i].isAlive) canvas.drawBitmap(enemiesRow1[i].getInvader(), -200f, -400f, paint)
                    if (enemiesRow1[i].isAlive) {
                        canvas.drawBitmap(enemiesRow1[i].getInvader(), enemiesRow1[i].x, enemiesRow1[i].y, paint)
                    }
                    i++
                }

                i = 0
                while(i < enemiesRow2.size){
                    if (!enemiesRow2[i].isAlive) canvas.drawBitmap(enemiesRow2[i].getInvader(), -200f, -400f, paint)
                    if (enemiesRow2[i].isAlive) {
                        canvas.drawBitmap(enemiesRow2[i].getInvader(), enemiesRow2[i].x, enemiesRow2[i].y, paint)
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
                    enemiesRow1.forEach { it.isAlive = true }
                    enemiesRow2.forEach { it.isAlive = true }
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
                enemBullets[i].rect.set(posX + enemiesRow1[0].width/2, posY, (posX + enemiesRow1[0].width/2 + enemBullets[i].width), (posY + enemBullets[i].height))
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