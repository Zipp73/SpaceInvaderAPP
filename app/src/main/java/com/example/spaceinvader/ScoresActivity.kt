package com.example.spaceinvader

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

val database = Firebase.database    //write
val mRootRef = database.reference    //write .getReference("players")

class ScoresActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)

            //WRITE
        //val player1 = Player("Luck", 3459876)//todo
        //addPlayerToList(player1)//TODO for test
        //loadDatabase(mRootRef)

        getData(mRootRef)
    }

    override fun onClick(v: View){
        when(v.id){
            R.id.bt_return -> finish()
            R.id.bt_refresh -> getData(mRootRef)
        }
    }

    fun getData(firebaseData: DatabaseReference) {//todo adjust
            //READ
        mRootRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.getValue<Player>()
                //Log.d(TAG, "Value is: $value")
                if (value != null) Toast.makeText(this@ScoresActivity, "$value", Toast.LENGTH_LONG).show()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

}
