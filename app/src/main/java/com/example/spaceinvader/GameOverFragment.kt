package com.example.spaceinvader
//TODO move this fragment to the end of the game
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class GameOverFragment : Fragment(), View.OnClickListener {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var pts: TextView
    private lateinit var playn: TextView
    private lateinit var twin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v: View = inflater.inflate(R.layout.fragment_game_over, container, false)

        twin = v.findViewById(R.id.tx_win)
        pts = v.findViewById(R.id.scoreval_tv)
        playn = v.findViewById(R.id.player_tv)

        if(GameView.win) {
            twin.text = "You Win"
            twin.setTextColor(Color.WHITE)
        }

        pts.text = GameActivity.score.toString()

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
            R.id.bt_save_score -> {
                //todo if condition
                toSave()
            }

            R.id.bt_returnMain -> toMain()
            R.id.bt_newGame -> toNew()
        }
    }

    private fun toSave() {
        val intent = Intent(this.activity, ScoresActivity::class.java)

        intent.putExtra("ok", 1)//todo resolve [ScoresActivity]

        if (playn.text.toString() == "") intent.putExtra("nick", "Unknown Player")
        else intent.putExtra("nick", playn.text.toString())

        intent.putExtra("score", pts.text.toString().toInt())

        startActivity(intent)
    }

    private fun toMain() {
        this.activity?.finish()/*todo?*/
    }

    private fun toNew(){
        val intent = Intent(this.context, GameActivity::class.java).apply{}
        startActivity(intent)
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            GameOverFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

