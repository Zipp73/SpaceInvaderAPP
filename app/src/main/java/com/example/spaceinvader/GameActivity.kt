package com.example.spaceinvader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowMetrics
import android.webkit.WebViewFragment
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class GameActivity : AppCompatActivity(), View.OnClickListener{
    companion object{
        var score = 0
    }

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
            R.id.bt_test -> toTest()    //todo remove after test
        }
    }

    fun changeText(v: View?){
        val textView : TextView = findViewById(R.id.t_score)
        textView.text = score.toString()
    }

    fun toPause(){
        //TODO pausing system of game-play
        changeText(null)
        val game : Fragment? = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        game?.onPause()
        val menu : Fragment = PauseMenu()
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, menu, menu.tag)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun toTest(){   //todo remove after test
        changeText(null)
        val game : Fragment? = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        game?.onPause()
        val test : Fragment = GameOverFragment()
        val fm : FragmentManager = supportFragmentManager
        val transaction : FragmentTransaction = fm.beginTransaction()
        transaction.add(R.id.container, test, test.tag)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}