package com.udacity

import android.app.Application
import android.content.Context


class LoadingApp: Application() {

    val notificationManager : NotificationManager by lazy { NotificationManager(this) }

    init {
        instance = this
    }

    companion object {
        var instance: LoadingApp? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }


    }

    override fun onCreate() {
        super.onCreate()
        notificationManager.createNotificationChannel()
    }

}