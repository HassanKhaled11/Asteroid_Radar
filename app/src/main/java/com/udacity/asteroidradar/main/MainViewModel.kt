package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.widget.ProgressBar
import androidx.lifecycle.*
import com.udacity.asteroidradar.PictureOfDay
//import com.udacity.asteroidradar.Data.AsteroidDataBase
//import com.udacity.asteroidradar.Data.LocalRepository
//import com.udacity.asteroidradar.Data.LocalRepositoryImp
import com.udacity.asteroidradar.database.AsteroidDataBase
import com.udacity.asteroidradar.database.LocalRepositoryImp
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.remote.RemoteRepositoryImp
import com.udacity.asteroidradar.remote.RetroBuilder
import com.udacity.asteroidradar.remote.ServiceAPI
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MainViewModel (app : Application) : AndroidViewModel(app) {

    private  var remoteRepositoryImp : RemoteRepositoryImp

    private lateinit var localRepositoryImp:LocalRepositoryImp


    private val AsteroidsListMutableLiveData = MutableLiveData<List<Asteroid>>()
    val AsteroidsListLiveData : LiveData<List<Asteroid>> get()= AsteroidsListMutableLiveData

     val flag = MutableLiveData<Boolean>(false)

    private val SavedAsteroidsMutableLiveData = MutableLiveData<List<Asteroid>>()
    val SavedAsteroidsLiveData : LiveData<List<Asteroid>> get()= SavedAsteroidsMutableLiveData


     val Pictureoftheday = MutableLiveData<PictureOfDay>()

    init {

        var db = AsteroidDataBase.getInstance(app)
        localRepositoryImp = LocalRepositoryImp(db)

        var saveInstance = RetroBuilder.getRetrofitBuilder().create(ServiceAPI::class.java)
        remoteRepositoryImp = RemoteRepositoryImp(saveInstance)

        getPicOfTheDay()

        }

     fun getAPIAsteroids() {

         viewModelScope.launch {

             try {
                 var result = remoteRepositoryImp.getData()
                 AsteroidsListMutableLiveData.value = result
                 deleteAsteroids()
                insertAsteroids(result)

             } catch (e: Exception) {
                 if (SavedAsteroidsLiveData != null) {
                     getSavedAsteroids()
                     Log.i("err", "Error loading Data ${e.message}")
                 } else {
                  Log.i("err", "Error loading Data ${e.message}")
                 }
             }
         }

         }


    fun getPicOfTheDay(){

        viewModelScope.launch {
            try {

                Pictureoftheday.value = remoteRepositoryImp.getPictureOfTheDay()

            } catch (e: Exception) {

                Log.i("pic Error", "${e.message}")
            }
        }
    }



    fun insertAsteroids(asteroidlist: List<Asteroid>){
        viewModelScope.launch {
            localRepositoryImp.insertAsteroids(asteroidlist)
            flag.value = true
        }

    }


     fun getSavedAsteroids() {
        viewModelScope.launch {
             SavedAsteroidsMutableLiveData.value = localRepositoryImp.getSavedAsteroids()
        }
    }


    fun deleteAsteroids(){
        viewModelScope.launch {
            localRepositoryImp.deleteAsteroids()
        }
    }
}


