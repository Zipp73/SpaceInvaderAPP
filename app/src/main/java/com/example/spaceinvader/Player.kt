package com.example.spaceinvader

import com.google.firebase.database.DatabaseReference

data class Player(val nickname: String = "", val score: Int = 0, var uuid: String= "")

    val players: MutableList<Player> = mutableListOf()/*TODO giusto qui?*/

    fun loadDatabase(firebaseData: DatabaseReference) {
        players.forEach {
            /*
            //val key = firebaseData.push().key                 //yes without uuid
            //if (key != null)    firebaseData.setValue(it)     //yes without uuid
            */


            val key = firebaseData.child("players").push().key
            if (key != null) it.uuid = key
            if (key != null)    firebaseData.child("players").setValue(it)
            //if (key != null) firebaseData.child("players").child(key).setValue(it)

        }
    }

    fun addPlayerToList(player: Player) {
        players.add(player)
    }






