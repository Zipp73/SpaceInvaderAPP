package com.example.spaceinvader

class Savedata(pX: Int, pY: Int, enemyAlive: Int, enemiesAlive: BooleanArray, eY: Float, enemySpeed: Int, score: Int) {
    var screenX : Int = 0
    var screenY : Int = 0
    var enemiesNumber : Int = 0
    var enemiesY : Float = 0f
    var score : Int = 0
    var speed : Int = 0
    lateinit var enemiesAlive : BooleanArray

    init {
        screenX = pX
        screenY = pY
        enemiesNumber = enemyAlive
        enemiesY = eY
        speed = enemySpeed
        this.score = score
        this.enemiesAlive = enemiesAlive
    }
}