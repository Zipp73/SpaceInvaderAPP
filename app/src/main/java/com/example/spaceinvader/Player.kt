package com.example.spaceinvader

import com.google.firebase.database.DatabaseReference


val players: MutableList<Player> = mutableListOf()/*TODO giusto qui?*/

class Player(val nickname: String = "", val score: Int = 0, var uuid: String= "")


    /*
    fun loadToDatabase(firebaseData: DatabaseReference) {
        players.forEach {//todo foreach?????????
            val key = firebaseData.child("players").push().key
            if (key != null) it.uuid = key
            if (key != null) firebaseData.child("players").child(key).setValue(it)
        }
    }
     */

fun loadToDatabase(firebaseData: DatabaseReference, player: Player) {
        val key = firebaseData.child("players").push().key
        if (key != null) player.uuid = key
        if (key != null) firebaseData.child("players").child(key).setValue(player)
}

fun writeOnDB(player: Player) {
    //if(player.nickname != "") players.add(player)
    //if(!contains(players, player)) players.add(player)//todo where to put this line, loadToDatabase?

    //loadToDatabase(mRootRef)//before
    loadToDatabase(mRootRef, player)//after
}



    fun equals(player1: Player, player2: Player): Boolean {//todo change equals method kotlin
        return (player1.nickname == player2.nickname)&&(player1.score == player2.score)&&(player1.uuid == player2.uuid)
    }

    fun contains(playerList: MutableList<Player>, player: Player): Boolean {//todo change contains method kotlin
        playerList.forEach {
            if(equals(it, player)) return true
        }
        return false
    }










