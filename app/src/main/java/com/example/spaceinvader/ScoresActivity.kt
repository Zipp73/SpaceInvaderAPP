package com.example.spaceinvader

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

val database = Firebase.database
//val mRootRef = database.getReference("players") //radice albero json
val mRootRef = database.reference

class ScoresActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)

            //WRITE
        val player = Player("Walu", 6454345)//todo
        addPlayerToList(player)//TODO for test
        loadDatabase(mRootRef)

            //READ
        getData()
    }

    override fun onClick(v: View){
        when(v.id){
            R.id.bt_return -> finish()
            R.id.bt_refresh -> getData()
        }
    }

    fun getData() {
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
