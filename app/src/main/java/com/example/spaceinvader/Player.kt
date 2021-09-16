package com.example.spaceinvader

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

 class Player(val nickname: String = "", val score: Int = 0, var uuid: String= "")

    val players: MutableList<Player> = mutableListOf()/*TODO giusto qui?*/

    fun loadToDatabase(firebaseData: DatabaseReference) {

        players.forEach {
            val key = firebaseData.child("players").push().key
            if (key != null)    it.uuid = key
            if (key != null) firebaseData.child("players").child(key).setValue(it)
        }
    }

    fun addPlayerToList(player: Player) {
        if(player.nickname != "") players.add(player)
    }






