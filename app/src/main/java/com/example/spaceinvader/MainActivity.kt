package com.example.spaceinvader

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess

val database = Firebase.database
val mRootRef = database.reference

class MainActivity() : AppCompatActivity(), View.OnClickListener, SensorEventListener {
    private lateinit var sensorManager : SensorManager
    private lateinit var logo : EnemyDraw

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logo = findViewById(R.id.iv_home)

        setUpSensorMovement()


        var nic = ""; var sco = 0; var uui = ""
            mRootRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val ply = snapshot!!.child("players").children

                    ply.forEach {
                        nic = it.getValue<Player>()?.nickname.toString()
                        sco = it.getValue<Player>()?.score!!
                        uui = it.getValue<Player>()?.uuid!!

                        if(!contains(players, Player(nic, sco, uui))) players.add(Player(nic, sco, uui))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
                }
            })

    }

    override fun onClick(v: View){
        when(v.id){
            R.id.bt_newGame -> toNewGame()
            R.id.bt_scores -> toScore()
            R.id.bt_exit -> toExit()
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

    private fun setUpSensorMovement(){
        sensorManager = this.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event?.sensor?.type == Sensor.TYPE_GYROSCOPE) {
            logo.apply {
                rotationX = event.values[0]*2
                rotationY = event.values[1]*2
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    private fun toLoadGame(){
        //todo
    }

    private fun toExit(){
        exitProcess(0)
    }

}