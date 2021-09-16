package com.example.spaceinvader

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
var i = 0//todo resolve [GameOverFragment]
class ScoresActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)


        val intent = intent
        val n: String = intent.getStringExtra("nick").toString()
        val s: Int = intent.getIntExtra("score", 0)
        if(i > 0) {//todo resolve [GameOverFragment]
            if (n != "") writeOnDB(Player(n, s))
            //if (n != "") addPlayerToList(Player(n, s))
        }


        //READ
        getData()


        val recyclerview = findViewById<RecyclerView>(R.id.recycler_view)

        recyclerview.layoutManager = LinearLayoutManager(this)

        //val players = ArrayList<Player>()
        //for (i in 1..2) { players.add(Player("Nick $i", i, "${i*874}")) }

        val adapter = CustomAdapter(players)//todo first item null!!!!!! resolve!
        recyclerview.adapter = adapter

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_return -> startActivity(Intent(this, MainActivity::class.java).apply {})
            R.id.bt_refresh -> getData()
        }
    }

    lateinit var t: TextView
    fun getData() { //todo move to Player class
        t = findViewById(R.id.test_tv)

        mRootRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                //val value = snapshot.child("players/").getValue<Player>()
                val value = snapshot.child("players/-MjiMkd8VikgW3ICluZo").getValue<Player>()

                if (value != null) {
                    t.setText("val: ${value.nickname} - pts: ${value.score}")
                    //addPlayerToList(Player(value.nickname, value.score, value.uuid))
                }

            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })

    }


}
