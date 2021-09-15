package com.example.spaceinvader

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

    //val database = Firebase.database
//val mRootRef = database.getReference("players") //radice albero json
    //val mRootRef = database.reference

class ScoresActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)

            //WRITE
        //val player0 = Player("abc", 4356)//todo
        //addPlayerToList(player0)//TODO for test
        //loadToDatabase(mRootRef)        //moved to GameOverFragment

        //val player1 = Player("def", 7456)//todo
        //addPlayerToList(player1)//TODO for test
        //loadToDatabase(mRootRef)        //moved to GameOverFragment

        val intent = intent
        val n: String = intent.getStringExtra("nick").toString()
        val s: Int = intent.getIntExtra("score", 1)
        val player = Player(n, s)

        writeOnDB(player)
            //READ
        getData()




        val recyclerview = findViewById<RecyclerView>(R.id.recycler_view)

        recyclerview.layoutManager = LinearLayoutManager(this)

        //val players = ArrayList<Player>()
        //for (i in 1..2) { players.add(Player("Nick $i", i, "${i*874}")) }

        val adapter = CustomAdapter(players)

        recyclerview.adapter = adapter
    }

    fun writeOnDB(player: Player) {
        addPlayerToList(player)//TODO for test
        loadToDatabase(mRootRef)
    }

    override fun onClick(v: View){
        when(v.id){
            R.id.bt_return -> finish()
            R.id.bt_refresh -> getData()
        }
    }



    fun getData() { //todo move to Player class
        val testtv: TextView = findViewById(R.id.test_tv)
        mRootRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                val value = snapshot.child("players/").getValue<Player>()
                //val value = snapshot.getValue<Player>()       //yes without uuid


                testtv.setText(value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
    }
}
