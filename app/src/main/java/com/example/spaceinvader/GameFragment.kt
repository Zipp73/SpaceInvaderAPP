package com.example.spaceinvader

import android.graphics.Point
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatDelegate

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class GameFragment : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var gameView : GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v = inflater.inflate(R.layout.fragment_game2, container, false)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        val point = Point()
        point.x = MainActivity.screenWidth - 24*2*MainActivity.densityPixelFactor.toInt()
                                                //padding container + padding fragment
        point.y = MainActivity.screenHeight - 24*2*MainActivity.densityPixelFactor.toInt() +
                - 48*MainActivity.densityPixelFactor.toInt() +  //button dimension
                - 24*MainActivity.densityPixelFactor.toInt() +  //container distance from button
                - 48*MainActivity.densityPixelFactor.toInt()    //action bar height
            //1640

        Toast.makeText(context, "T:" + point.x + " || " + point.y, Toast.LENGTH_LONG).show()

        gameView = GameView(v.context, point.x, point.y)

        //Toast.makeText(context, "Tap the Screen to start the Game... \n AND HAVE FUN!", Toast.LENGTH_LONG).show()

        return gameView
    }

    override fun onPause(){
        super.onPause()
        gameView.pause()
    }

    override fun onResume(){
        super.onResume()
        gameView.resume()
    }

    companion object {
        @JvmStatic fun newInstance(param1: String, param2: String) =
                GameFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}