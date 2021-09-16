package com.example.spaceinvader

import com.google.firebase.database.DatabaseReference


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
        addPlayerToList(player)//TODO disabled for the moment
        loadToDatabase(mRootRef)
    }


    fun addPlayerToList(player: Player) {
        if(player.nickname != "") players.add(player)
    }








