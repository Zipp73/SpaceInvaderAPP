package com.example.spaceinvader

import com.google.firebase.database.DatabaseReference

data class Player(val nickname: String = "", val score: Int = 0)
val players: MutableList<Player> = mutableListOf()/*TODO giusto qui?*/

fun loadDatabase(firebaseData: DatabaseReference) {
    players.forEach {
        val key = firebaseData.child("players").push().key
        if (key != null) {
            firebaseData.child("players").child(key).setValue(it)
        }
    }
}

fun addPlayerToList(player: Player) {
    players.add(player)
}

/*
fun loadDatabase(firebaseData: DatabaseReference) {
    players.forEach {
        val key = firebaseData.child("players").push().key
        it.uuid = key
        firebaseData.child("players").child(key).setValue(it)
    }
}
 */



