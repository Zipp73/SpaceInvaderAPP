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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class GameView(context: Context, screenX: Int, screenY: Int) : SurfaceView(context), SensorEventListener, Runnable{
    private lateinit var gameThread : Thread
    private var isPlaying = false
    private var screenX = 0
    private var screenY = 0
    private val paint : Paint = Paint()
    private var pc : PlayableCharacter = PlayableCharacter(-100, -100, resources)
    private var enemies : MutableList<Enemy> = mutableListOf()
    private lateinit var sensorManager : SensorManager
    val MAX_FRAME_TIME = 1000/60
    var isGameOver = false
    private var enemBullets : MutableList<Bullet> = mutableListOf()
    private val maxEnemBullets = 30
    private var score = 0
    private var enemyAlive = -1
    private var enemyXLevel = 18
    private var enemiesAlive = BooleanArray(enemyXLevel)
    private var enemySpeed = 0
    private var enemyGoingLeft = false
    private var level = 0
    companion object{
        var win = false
        var save : Savedata = Savedata(0, 0, 0, BooleanArray(18), 0f, 0, 0)
    }

    init{
        this.screenX = screenX
        this.screenY = screenY

        val b = BooleanArray(enemyXLevel)
        var i = 0
        while(i < b.size){
            b[i++] = true
        }

        setSavedata(screenX, screenY, enemyXLevel, b, 0f, 1, 0)
        while(enemBullets.size < maxEnemBullets) enemBullets.add(Bullet())
    }



    private fun setEnemyPosition(l: Int, eposY: Float){
        when(l%2){
            0 -> {
                val enemyXPos = FloatArray(enemies.size / 3)
                val pos = (screenX - 48f) / ((screenX - 192f) / enemies[0].width)
                i = 0
                while (i < enemyXPos.size) {
                    enemyXPos[i] = 48 + (i * pos)
                    enemies[i].y = eposY
                    enemies[i + enemyXPos.size].y = (enemies[i].height + 8).toFloat() + eposY
                    enemies[i + 2*enemyXPos.size].y = (enemies[i].height + 8).toFloat() * 2 + eposY
                    enemies[i].x = enemyXPos[i]
                    enemies[i + enemyXPos.size].x = enemyXPos[i]
                    enemies[i + 2*enemyXPos.size].x = enemyXPos[i]
                    i++
                }
            }
            /*1 -> {
                val enemyXPos = FloatArray(enemies.size / 2)
                val pos = (screenX - 48f) / ((screenX - 192f) / enemies[0].width)
                i = 0
                while (i < enemyXPos.size) {
                    enemyXPos[i] = 48 + (i * pos)
                    enemies[i].y = 0f
                    enemies[i + enemyXPos.size].y = (enemies[i].height + 8).toFloat()
                    enemies[i + enemyXPos.size + enemyXPos.size/2].y = (2 * enemies[i].height + 8).toFloat()
                    enemies[i].x = enemyXPos[i]
                    enemies[i + enemyXPos.size].x = enemyXPos[i ]
                    enemies[i + enemyXPos.size + enemyXPos.size/2].y = (2 * enemies[i].height + 8).toFloat()
                    i++
                }
            }*/
        }
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

        //player's bullet collision
        pc.bullets.forEach{
            if(it.rect.top < -124) it.isActive = false
            if(it.isActive) it.rect.set(it.rect.left, (it.rect.top - 15), it.rect.right, (it.rect.bottom - 15))
            for(e: Enemy in enemies) {
                if(RectF.intersects(e.getCollisionShape(), it.getCollisionShape())) {
                    score += (enemies.size + 1 - enemyAlive)
                    e.x = -100f -e.width.toFloat()
                    it.rect.set(0f, -100f, it.width, (-100f+it.height))
                    e.isAlive = false
                    enemyAlive--
                    enemySpeed = 24 / (enemyAlive + 1)
                    GameActivity.score = score
                }
            }
        }

        //enemies' shoot & movement
        enemies.forEach{
            if(it.isAlive){
                if(it.takeAim(pc.x, pc.width.toFloat())) enemShoot(it.x, it.y)
                if (enemyGoingLeft) it.x -= enemySpeed
                if (!enemyGoingLeft) it.x += enemySpeed
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

        //enemies' bullets
        enemBullets.forEach{
            if(it.rect.top > screenY + it.height + 100f) it.isActive = false
            if(it.isActive) it.rect.set(it.rect.left, (it.rect.top + 10), it.rect.right, (it.rect.bottom + 10))
            if(RectF.intersects(pc.getCollisionShape(), it.getCollisionShape())) isGameOver = true
        }

        //player won the level
        if(enemyAlive == 0) {
            isGameOver = true
            win = true
            //level++
        }
    }

    private fun enemiesGoingDown(){
        enemies.forEach {
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
                while(i < enemies.size){
                    if (!enemies[i].isAlive) canvas.drawBitmap(enemies[i].getInvader(), -200f, -400f, paint)
                    if (enemies[i].isAlive) {
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
        //set savedata


        isPlaying = true
        gameThread = Thread(this)
        gameThread.start()
    }

    fun pause(){
        //get savedata
        save = getSavedata()

        isPlaying = false
        gameThread.join()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN -> { }
            MotionEvent.ACTION_UP -> {
                //Toast.makeText(context, "T: " + pc.x + " || " + pc.y, Toast.LENGTH_SHORT).show()
                if(pc.nextShot == -1) {
                    enemies.forEach {
                        it.isAlive = true
                    }
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

    private fun setSavedata(pposX: Int, pposY: Int, actualEnemy: Int, eneAlive: BooleanArray, eposY: Float, actualSpeed: Int, actualScore: Int){
        //player's ship
        pc = PlayableCharacter(pposX, pposY, resources)

        //enemies
        while(enemies.size < enemyXLevel) enemies.add(Enemy(resources, this.screenX))
        setEnemyPosition(level, eposY)

        //enemies
        enemyAlive = actualEnemy
        enemiesAlive = eneAlive
        var i = 0
        while(i < enemiesAlive.size) enemies[i].isAlive = enemiesAlive[i++]
        while(enemBullets.size < maxEnemBullets) enemBullets.add(Bullet())

        //gameplay info
        enemySpeed = actualSpeed
        score = actualScore
        GameActivity.score = score
        win = false

        //colors
        paint.color = Color.WHITE
        paint.isAntiAlias = true

        save = getSavedata()
    }

    fun getSavedata(): Savedata{
        var i = 0
        while(i < enemiesAlive.size){
            enemiesAlive[i] = enemies[i++].isAlive
        }
        save = Savedata(screenX, screenY, enemyAlive, enemiesAlive, enemies[1].y, enemySpeed, score)
        return(Savedata(screenX, screenY, enemyAlive, enemiesAlive, enemies[1].y, enemySpeed, score))
    }
}