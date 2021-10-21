package com.example.spaceinvader

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PauseMenu : Fragment(), View.OnClickListener {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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
            R.id.bt_save -> toSave()
        }
    }

    private fun toGame() {
        val f : Fragment? = activity?.supportFragmentManager?.findFragmentById(R.id.fragmentContainerView)
        f?.onResume()
        this.activity?.onBackPressed()
    }

    private fun toMain() {
        this.activity?.finish()
    }

    private fun toSave(){
        //todo save game
        return
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            PauseMenu().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}