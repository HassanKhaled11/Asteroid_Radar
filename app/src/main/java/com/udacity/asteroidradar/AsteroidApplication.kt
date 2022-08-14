package com.udacity.asteroidradar

import android.app.Application
import android.os.Build
import androidx.work.*
import com.udacity.asteroidradar.work.DataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidApplication : Application() {

    val aplicationScope = CoroutineScope(Dispatchers.Default)

    private fun delayedInit(){
        aplicationScope.launch {
            setWork()
        }
    }

    private fun setWork() {

        val constrains = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.METERED)
            .setRequiresBatteryNotLow(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val repeatRequest = PeriodicWorkRequestBuilder<DataWorker>(1,TimeUnit.DAYS)
            .setConstraints(constrains)
            .build()


        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(DataWorker.WORK_NAME,
        ExistingPeriodicWorkPolicy.KEEP,
        repeatRequest)
    }

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }


}