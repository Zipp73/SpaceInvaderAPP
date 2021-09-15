package com.example.spaceinvader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

val database = Firebase.database    //todo here?
val mRootRef = database.reference   //todo here?

class MainActivity() : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //loadDatabase(mRootRef) //write
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

    private fun toLoadGame(){
        //todo
    }

    private fun toExit(){
        finish()
    }
}