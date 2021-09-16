package com.example.spaceinvader

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Paint
import android.graphics.Point
import android.graphics.RectF
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat.*
import androidx.fragment.app.FragmentContainerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Game.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment()/*, View.OnTouchListener, SensorEventListener */{
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
        /*v.viewTreeObserver.addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener{
            fun onGlobalLayout(){
                val f = v.findViewById<FragmentContainerView>(R.id.fragmentContainerView)
                point.x = f.width
                point.y = f.height
            }
        })*/
        point.x = 900
        point.y = 1400

        gameView = GameView(v.context, point.x, point.y)

        Toast.makeText(context, "HAY :" + point.x + " , " + point.y + " , " + gameView.control, Toast.LENGTH_LONG).show()

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