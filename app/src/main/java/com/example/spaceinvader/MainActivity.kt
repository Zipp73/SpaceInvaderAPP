package com.example.spaceinvader

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.*
import java.lang.StringBuilder

val database = Firebase.database
val mRootRef = database.reference

class MainActivity : AppCompatActivity(), View.OnClickListener, SensorEventListener{

    private lateinit var sensorManager : SensorManager
    private lateinit var logo : EnemyDraw

    companion object{
        var t : String = "Standard"
        var screenHeight = -1
        var screenWidth = -1
        var densityPixelFactor = -1f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        screenHeight = Resources.getSystem().displayMetrics.heightPixels
        screenWidth = Resources.getSystem().displayMetrics.widthPixels

        when(Resources.getSystem().displayMetrics.densityDpi){
            in 0..120   -> densityPixelFactor = 0.75f
            in 121..160 -> densityPixelFactor = 1f
            in 161..240 -> densityPixelFactor = 1.5f
            in 241..320 -> densityPixelFactor = 2f
            in 321..480 -> densityPixelFactor = 3f
            in 481..640 -> densityPixelFactor = 4f
        }

        //read file for theme

        when(t){
            "Standard"  -> setTheme(R.style.Theme_SpaceInvader)
            "Pink"      -> setTheme(R.style.DarkPink)
            "Cyan"      -> setTheme(R.style.DarkCyan)
            "Red"       -> setTheme(R.style.DarkRed)
            "Green"     -> setTheme(R.style.DarkGreen)
        }

        setContentView(R.layout.activity_main)

        setUpSensorMovement()

        //Toast.makeText(this, screenHeight.toString() + " , " + screenWidth.toString() + " , " + densityPixelFactor.toInt().toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()

        when(t){
            "Standard"  -> setTheme(R.style.Theme_SpaceInvader)
            "Pink"      -> setTheme(R.style.DarkPink)
            "Cyan"      -> setTheme(R.style.DarkCyan)
            "Red"       -> setTheme(R.style.DarkRed)
            "Green"     -> setTheme(R.style.DarkGreen)
        }
        setContentView(R.layout.activity_main)
    }

    override fun onClick(v: View){
        when(v.id){
            R.id.bt_newGame     -> toNewGame()
            R.id.bt_scores      -> toScore()
            R.id.bt_exit        -> toExit()
            R.id.bt_settings    -> toSetting()
            R.id.bt_loadGame    -> toLoadGame()
        }
    }

    private fun toNewGame(){
        val intent = Intent(this, GameActivity::class.java).apply{}
        startActivity(intent)
    }

    private fun toScore(){
        val intent = Intent(this, ScoresActivity::class.java).apply {}
        startActivity(intent)
    }

    private fun toSetting(){
        val intent = Intent(this, SettingActivity::class.java).apply {}
        startActivity(intent)
    }

    private fun toLoadGame(){
        //todo
    }

    private fun toExit(){
        finish()
    }

    private fun setUpSensorMovement(){
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        logo = findViewById(R.id.iv_home)
        if(event?.sensor?.type == Sensor.TYPE_GYROSCOPE) {
            logo.apply{
                rotationX += event.values[0]/4
                rotationY += event.values[1]/4
                rotation += event.values[2]/4
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }
}