package com.example.spaceinvader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity() : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClick(v: View){
        when(v.id){
            R.id.bt_newGame -> toNewGame()
            R.id.bt_scores -> toScore()
            R.id.bt_exit -> toExit()
            else -> return
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

    private fun toExit(){
        onDestroy()
    }
}