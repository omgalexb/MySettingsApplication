package com.lexxapps.mysettings

import android.app.Application
import com.lexxapps.mysettings.data.AppContainer
import com.lexxapps.mysettings.data.AppDataContainer

class MySettingsApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}