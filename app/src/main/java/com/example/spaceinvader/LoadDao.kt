package com.example.spaceinvader

import androidx.room.*

@Dao //Data Access Object
interface LoadDao {
    //@Query("SELECT * FROM load_table")
    //fun getAll(): List<Load>

    @Query("SELECT * FROM load_table WHERE uid LIKE (0)")
    fun getGameZero(): Load

    //@Query("SELECT * FROM load_table WHERE uid IN (:gameIds)")
    //fun loadAllByIds(gameIds: IntArray): List<Load>

    //@Query("SELECT * FROM load_table WHERE first_name LIKE (:first) AND last_name LIKE (:last) LIMIT 1")
    //fun findByName(first: String, last: String): Load

    //@Insert
    //fun insertAll(vararg loads: Load)

    @Insert(onConflict = OnConflictStrategy.IGNORE)//temp
    fun insertGame(loads: Load)

    @Delete
    fun delete(load: Load)

    //@Update
    //fun update(load: Load)


    @Query("SELECT * FROM load_table")
    fun getAll(): List<Load>

}