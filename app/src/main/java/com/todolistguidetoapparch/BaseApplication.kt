package com.todolistguidetoapparch

import android.app.Application
import androidx.annotation.StringRes
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    companion object {
        lateinit var instance: BaseApplication private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}