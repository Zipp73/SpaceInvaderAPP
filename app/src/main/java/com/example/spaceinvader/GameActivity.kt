package com.example.spaceinvader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

class GameActivity : AppCompatActivity(), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val v : View = findViewById(R.id.fragmentContainerView)
        v.visibility = View.INVISIBLE
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.bt_pause -> toPause()
        }
    }

    fun toPause(){
        val v : View = findViewById(R.id.fragmentContainerView)
        v.visibility = View.VISIBLE
    }
}