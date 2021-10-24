package com.example.spaceinvader

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

var i = 0//todo resolve [GameOverFragment]
class ScoresActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when(MainActivity.t){
            "Standard" -> setTheme(R.style.Theme_SpaceInvader)
            "Pink" -> setTheme(R.style.DarkPink)
            "Blue" -> setTheme(R.style.DarkCyan)
            "Red" -> setTheme(R.style.DarkRed)
            "Green" -> setTheme(R.style.DarkGreen)
        }
        setContentView(R.layout.activity_scores)


        val intent = intent
        i = intent.getIntExtra("ok", 0)//todo resolve [GameOverFragment]
        if(i > 0) {
            val n: String = intent.getStringExtra("nick").toString()
            val s: Int = intent.getIntExtra("score", 0)
            if (n != "") writeOnDB(Player(n, s, ""))//todo uuid?
            //if (n != "") addPlayerToList(Player(n, s))
        }


        val recyclerview = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerview.layoutManager = LinearLayoutManager(this)


        getData()//READ//todo here? getData()

        players = mergesort(players)

        val adapter = CustomAdapter(players)
        recyclerview.adapter = adapter

    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_return -> {
                startActivity(Intent(this, MainActivity::class.java).apply {})
                finish()
            }
            R.id.bt_refresh -> getData()//todo intent to return to the creation of this activity??
        }
    }

    //todo move to Player class?
    var nic = "";    var sco = 0;    var uui = ""
    //lateinit var t: TextView//todo test
    fun getData() {
        mRootRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val ply = snapshot.child("players").children

                //t = findViewById(R.id.test_tv)//todo test

                ply.forEach {
                    nic = it.getValue<Player>()?.nickname.toString()
                    sco = it.getValue<Player>()?.score!!
                    uui = it.getValue<Player>()?.uuid!!

                    if(!contains(players, Player(nic, sco, uui))) players.add(Player(nic, sco, uui))
                }

                //t.text = players.toString()//todo test
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })

    }

    fun mergesort(list: MutableList<Player>): MutableList<Player> {
        if (list.size <= 1) return list

        val mid = list.size / 2
        val left = list.subList(0,mid)
        val right = list.subList(mid,list.size)

        return merge(mergesort(left), mergesort(right))
    }

    fun merge(left: MutableList<Player>, right: MutableList<Player>): MutableList<Player> {
        var indexLeft = 0
        var indexRight = 0
        val newList : MutableList<Player> = mutableListOf()

        while (indexLeft < left.count() && indexRight < right.count()) {
            if (left[indexLeft].score >= right[indexRight].score) {
                newList.add(left[indexLeft])
                indexLeft++
            } else {
                newList.add(right[indexRight])
                indexRight++
            }
        }

        while (indexLeft < left.size) {
            newList.add(left[indexLeft])
            indexLeft++
        }

        while (indexRight < right.size) {
            newList.add(right[indexRight])
            indexRight++
        }
        return newList
    }

    //todo caricamento finale partita in db
}
