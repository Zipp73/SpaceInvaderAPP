package com.example.spaceinvader

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "load_table")
data class Load (
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "px") val pX: Int,
    @ColumnInfo(name = "py") val pY: Int,
    @ColumnInfo(name = "alive") val enemyAlive: Int,
    @ColumnInfo(name = "bool_alive") val enemiesAlive: BooleanArray,
    @ColumnInfo(name = "ey") val eY: Float,
    @ColumnInfo(name = "speed") val enemySpeed: Int,
    @ColumnInfo(name = "score") val score: Int,
)
