package com.example.spaceinvader

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Load::class], version = 1)
abstract class LoadDatabase: RoomDatabase() {
    /*
    companion object {
        fun get(application: Application): LoadDatabase {
            return Room.databaseBuilder(application, LoadDatabase::class.java, "space.db").build()
        }
    }*/

    abstract fun loadDao(): LoadDao
}

