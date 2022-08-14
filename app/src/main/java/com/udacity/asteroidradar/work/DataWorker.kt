package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.AsteroidApplication
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.end_date
import com.udacity.asteroidradar.api.start_date
import com.udacity.asteroidradar.database.AsteroidDataBase
import com.udacity.asteroidradar.database.AsteroidDataBase.Companion.getInstance
import com.udacity.asteroidradar.database.LocalRepositoryImp
import com.udacity.asteroidradar.remote.RemoteRepositoryImp
import com.udacity.asteroidradar.remote.RetroBuilder
import com.udacity.asteroidradar.remote.ServiceAPI
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DataWorker(context:Context , params : WorkerParameters) : CoroutineWorker(context,params)
{
    companion object{
        const val WORK_NAME = "DataWorker"
    }

    override suspend fun doWork(): Result {
         val database =  AsteroidDataBase.getInstance(applicationContext)
         val repositoryImp = LocalRepositoryImp(database)

        var saveInstance = RetroBuilder.getRetrofitBuilder().create(ServiceAPI::class.java)
        var remoteRepositoryImp = RemoteRepositoryImp(saveInstance)


         start_date = getNextSevenDaysFormattedDates().get(0)
         end_date = getNextSevenDaysFormattedDates().get(6)

        return try {
            remoteRepositoryImp.getPictureOfTheDay()
            var result = remoteRepositoryImp.getData()
            repositoryImp.deleteAsteroids()
            repositoryImp.insertAsteroids(result)

            Result.success()

        }catch (e:HttpException){

            Result.retry()

        }

    }


    private fun getNextSevenDaysFormattedDates(): ArrayList<String> {
        val formattedDateList = ArrayList<String>()

        val calendar = Calendar.getInstance()
        for (i in 0..Constants.DEFAULT_END_DATE_DAYS) {
            val currentTime = calendar.time
            val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
            formattedDateList.add(dateFormat.format(currentTime))
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        return formattedDateList
    }


}