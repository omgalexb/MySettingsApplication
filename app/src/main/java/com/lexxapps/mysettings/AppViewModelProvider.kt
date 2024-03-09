package com.lexxapps.mysettings

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.lexxapps.mysettings.ui.screens.settings.SettingsViewModel

object AppViewModelProvider {
    val factory = viewModelFactory {
        initializer {
            SettingsViewModel(
                myApplication().container.userPreferencesRepository
            )
        }
    }
}

fun CreationExtras.myApplication(): MySettingsApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MySettingsApplication)