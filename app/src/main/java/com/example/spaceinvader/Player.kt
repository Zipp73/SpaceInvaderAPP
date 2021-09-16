package com.example.spaceinvader

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


val players: MutableList<Player> = mutableListOf()/*TODO giusto qui?*/

class Player(val nickname: String = "", val score: Int = 0, var uuid: String= "")


    fun loadToDatabase(firebaseData: DatabaseReference) {

        players.forEach {
            val key = firebaseData.child("players").push().key
            if (key != null)    it.uuid = key
            if (key != null) firebaseData.child("players").child(key).setValue(it)
        }
    }


    fun writeOnDB(player: Player) {
        addPlayerToList(player)//TODO for test
        loadToDatabase(mRootRef)
    }


    fun addPlayerToList(player: Player) {
        if(player.nickname != "") players.add(player)
    }








