package com.example.spaceinvader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class GameActivity : AppCompatActivity(), View.OnClickListener{
    companion object{
        var score = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when(MainActivity.t){
            "Standard" -> setTheme(R.style.Theme_SpaceInvader)
            "Pink" -> setTheme(R.style.DarkPink)
            "Blue" -> setTheme(R.style.DarkBlue)
            "Red" -> setTheme(R.style.DarkRed)
            "Green" -> setTheme(R.style.DarkGreen)
        }
        setContentView(R.layout.activity_game)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.bt_pause -> toPause()
        }
    }

    fun toPause(){
        val game : Fragment? = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        game?.onPause()
        val menu : Fragment = PauseMenu()
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, menu, menu.tag)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}