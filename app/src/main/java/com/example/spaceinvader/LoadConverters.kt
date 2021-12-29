package com.example.spaceinvader

import androidx.room.TypeConverter

class LoadConverters {

    @TypeConverter
    fun FromBoolArray(enemiesAlive: BooleanArray) : String {
        var str: String = ""
        for(b:Boolean in enemiesAlive) str+=(b.toString() + ',')
        return str
    }

    @TypeConverter
    fun ToBoolArray(str: String) : BooleanArray {
        var alive: List<String> = str.split(',')
        var enemiesAlive = BooleanArray(alive.size)
        var i = 0
        for(s:String in alive) enemiesAlive[i++] = s.toBoolean()
        return enemiesAlive
    }
}