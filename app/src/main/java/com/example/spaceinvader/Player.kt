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

//data class Player(val nickname: String = "", val score: Int = 0, var uuid: String = "")
/*fun loadDatabase(firebaseData: DatabaseReference) {//TODO cambiare sturttura dati
    val players: List<Player> = mutableListOf(
        Player("Walu", 9385743),
        Player("Bows", 6843854),
        Player("Cayu", 2894326)
    )
    players.forEach {
        val key = firebaseData.child("players").push().key
        if (key != null) {
            it.uuid = key
        }
        if (key != null) {
            firebaseData.child("players").child(key).setValue(it)
        }
    }
}*/