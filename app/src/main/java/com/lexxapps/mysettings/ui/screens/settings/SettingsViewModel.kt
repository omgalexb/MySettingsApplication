package com.lexxapps.mysettings.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexxapps.mysettings.data.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow
    val uiState: StateFlow<SettingsUiState> = userPreferencesFlow.map {
        SettingsUiState(
            isDarkTheme = it.isDarkTheme,
            language = it.language
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = SettingsUiState()
    )

    fun updateTheme(theme: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.updateTheme(theme)
        }
    }

    fun updateLanguage(language: String) {
        viewModelScope.launch {
            userPreferencesRepository.updateLanguage(language)
        }
    }
}