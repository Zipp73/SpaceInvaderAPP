package com.example.spaceinvader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class GameActivity : AppCompatActivity(), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        /*val f : Fragment = GameFragment()
        val fm : FragmentManager = supportFragmentManager
        val transaction : FragmentTransaction = fm.beginTransaction()
        transaction.add(R.id.container, f, f.tag)
        transaction.addToBackStack(null)
        transaction.commit()*/
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.bt_pause -> toPause()
        }
    }

    fun toPause(){
        //TODO pausing system of game-play
        val menu : Fragment = PauseMenu()
        val fm : FragmentManager = supportFragmentManager
        val transaction : FragmentTransaction = fm.beginTransaction()
        transaction.add(R.id.container, menu, menu.tag)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}