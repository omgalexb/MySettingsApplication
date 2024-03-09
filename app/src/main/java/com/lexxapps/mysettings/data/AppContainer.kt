package com.lexxapps.mysettings.data

import android.content.Context

interface AppContainer {
    val userPreferencesRepository: UserPreferencesRepository
}

class AppDataContainer(context: Context) : AppContainer {
    override val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(context)
    }
}