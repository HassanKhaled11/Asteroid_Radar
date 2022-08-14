package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.udacity.asteroidradar.model.Asteroid
import androidx.room.*

@Dao
interface AsteroidDAO  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroid(asteroidlist: List<Asteroid>)

    @Query("DELETE FROM ASTEROID_TABLE")
    suspend fun deleteAsteroids()

    @Query("select * from asteroid_table")
    suspend fun getSavedAsteroids():List<Asteroid>

    @Query("select * from asteroid_table")
    suspend fun getWeekAsteroids():List<Asteroid>

    @Query("select * from asteroid_table")
    suspend fun getTodayAsteroids():List<Asteroid>

    @Query("select * from asteroid_table ORDER BY closeApproachDate ASC")
    fun getAsteroids(): LiveData<List<Asteroid>>











}