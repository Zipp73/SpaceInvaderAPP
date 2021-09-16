package com.example.spaceinvader
//TODO move this fragment to the end of the game
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GameOverFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameOverFragment : Fragment(), View.OnClickListener {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var pts: TextView
    private lateinit var playn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_game_over, container, false)

        pts = v.findViewById(R.id.scoreval_tv)
        playn = v.findViewById(R.id.player_tv)

        val saveSC: Button = v.findViewById(R.id.bt_save_score)
        saveSC.setOnClickListener(this)
        val b1 : Button = v.findViewById(R.id.bt_returnMain)
        b1.setOnClickListener(this)
        val new : Button = v.findViewById(R.id.bt_newGame)
        new.setOnClickListener(this)
        return v
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.bt_save_score -> toSave()
            R.id.bt_returnMain -> toMain()
            R.id.bt_newGame -> toNew()
        }
    }

    private fun toSave() {
        i=1//todo resolve [ScoresActivity]
        val intent = Intent(this.activity, ScoresActivity::class.java)

        if (playn.text.toString() == "") intent.putExtra("nick", "Unknown Player")
        else intent.putExtra("nick", playn.text.toString())

        intent.putExtra("score", pts.text.toString().toInt())

        startActivity(intent)
    }

    private fun toMain() {
        this.activity?.finish()
    }

    private fun toNew(){
        val intent = Intent(this.context, GameActivity::class.java).apply{}
        startActivity(intent)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GameOverFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GameOverFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

