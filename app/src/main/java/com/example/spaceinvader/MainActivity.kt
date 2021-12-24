package com.example.spaceinvader

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.database.Cursor
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess

val database = Firebase.database
val mRootRef = database.reference
const val T_KEY = "T_KEY"

lateinit var db: LoadDatabase
lateinit var loadDao: LoadDao

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

        t = loadData()//save theme [saveData -> SettingActivity]

        when(t){
            "Standard"  -> setTheme(R.style.Theme_SpaceInvader)
            "Pink"      -> setTheme(R.style.DarkPink)
            "Cyan"      -> setTheme(R.style.DarkCyan)
            "Red"       -> setTheme(R.style.DarkRed)
            "Green"     -> setTheme(R.style.DarkGreen)
        }

        setContentView(R.layout.activity_main)

        setUpSensorMovement()


        var nic: String
        var sco: Int
        var uui: String
        mRootRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val ply = snapshot.child("players").children

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

        //Room database
        db = Room.databaseBuilder(applicationContext, LoadDatabase::class.java, "space.db").allowMainThreadQueries().build()
        loadDao = db.loadDao()
    }

    override fun onResume() {
        super.onResume()

        t = loadData()

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
        val intent = Intent(this, GameActivity::class.java).apply{}
        startActivity(intent)



        //todo chose where to move????????????????????????
        //TODO create a local server using Room!!!
        //for the moment we store only one game but in the future we can save more games and chose which one to load


        //onCreate-MainActivity     db = Room.databaseBuilder(applicationContext, LoadDatabase::class.java, "space.db").allowMainThreadQueries().build() //try1
        //onCreate-MainActivity     loadDao = db.loadDao() //try1

        //toSave-PauseMenu          loadDao.insertGame(Load(0, "aijejie","brazzorf"))



        /*  //test single
        val load: Load = loadDao.getGameZero()
        val tv: TextView = findViewById(R.id.nem_tv)
        tv.text = load.firstName + "-" + load.lastName
        */

        /*  //test all
        val ll: List<Load> = loadDao.getAll()
        val tv: TextView = findViewById(R.id.nem_tv)
        tv.text = ll.toString()
        */





        /*  //TODO move to GameView?
        val load: Load = loadDao.getGameZero()
        if(load != null) {


            //replace all value



            loadDao.delete(Load(0, "aijejie","brazzorf"))
        }
        */


    }

    /*class InsertLoadData(val game: Load, val application: Application): AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            LoadDatabase.getdb(application).loadDao().insertGame(game)
            val ld: Load = LoadDatabase.getdb(application).loadDao().getGameZero()


            tv.text = ld.firstName + "-" + ld.lastName

            return null
        }
    }*/


    private fun toExit(){
        finish()
        exitProcess(0)
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

    private fun loadData(): String {    //save theme [saveData -> SettingActivity]
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString(T_KEY, null)

        return savedString.toString()
    }

}