package com.example.spaceinvader

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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




        val recyclerview = findViewById<RecyclerView>(R.id.recycler_view)

        recyclerview.layoutManager = LinearLayoutManager(this)

        //val players = ArrayList<Player>()
        for (i in 1..20) {
            players.add(Player("Nick $i", i, "${i*874}"))
        }

        val adapter = CustomAdapter(players)

        recyclerview.adapter = adapter
    }

    /*
    // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recycler_view)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            //data.add(ItemsViewModel(R.drawable.ic_baseline_folder_24, "Item " + i))
            data.add(ItemsViewModel(R.drawable.ic_launcher_foreground, "Item " + i))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
     */

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
