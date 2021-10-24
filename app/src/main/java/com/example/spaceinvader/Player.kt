package com.example.spaceinvader

import com.google.firebase.database.DatabaseReference


var players: MutableList<Player> = mutableListOf()//TODO giusto qui? //changed from val for mergesort ScoresActivity-MainActivity

class Player(val nickname: String = "", val score: Int = 0, var uuid : String = "")


    fun loadToDatabase(firebaseData: DatabaseReference, player: Player) {
            val key = firebaseData.child("players").push().key
            if (key != null) player.uuid = key
            if (key != null) firebaseData.child("players").child(key).setValue(player)
    }

    fun writeOnDB(player: Player) {
        loadToDatabase(mRootRef, player)

        putSort(player)
        //if(!contains(players, player)) players.add(player)//todo where to put this line, loadToDatabase?
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

    fun putSort(p: Player) {//if == score it put on top not todo bottom
        var i = 0
        val templ = players
        val resl: MutableList<Player> = mutableListOf()

        while (p.score < templ[i].score) {
            resl.add(templ[i])
            templ.removeAt(i)
            i++
        }

        resl.add(p)
        resl += templ
        players = resl
    }






