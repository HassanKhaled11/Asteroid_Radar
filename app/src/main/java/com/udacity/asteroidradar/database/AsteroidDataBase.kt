package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.udacity.asteroidradar.model.Asteroid


private  const val DATABASE_NAME = "asteroid_database"

@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
abstract class AsteroidDataBase : RoomDatabase() {

abstract fun asteroidDAO():AsteroidDAO

companion object{
    @Volatile
     private var instance : AsteroidDataBase?= null

    fun getInstance(context: Context):AsteroidDataBase{
        return  instance?: synchronized(Any()){
            instance?: buildDatabase(context).also{instance = it}
        }
    }

    private fun buildDatabase(context: Context): AsteroidDataBase {
        return  Room.databaseBuilder(context.applicationContext,
            AsteroidDataBase::class.java, DATABASE_NAME
        ).build()
    }



}

}