package com.example.spaceinvader

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Game.newInstance] factory method to
 * create an instance of this fragment.
 */
class Game : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v : View = inflater.inflate(R.layout.fragment_game, container, false)
        val b1 : Button = v.findViewById(R.id.bt_returnMain)
        b1.setOnClickListener(this)
        val b2 : Button = v.findViewById(R.id.bt_save)
        b2.setOnClickListener(this)
        val b3 : Button = v.findViewById(R.id.bt_toGame)
        b3.setOnClickListener(this)
        return v
    }

    override fun onClick(v: View) {
        //todo
        when(v.id){
            R.id.bt_toGame -> toGame()
            R.id.bt_returnMain -> toMain()
            R.id.bt_save -> return
        }
    }

    private fun toMain() {
        //todo
        this.activity?.onBackPressed()
    }

    private fun toGame() {
        //val f : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        //f?.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Game.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Game().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}