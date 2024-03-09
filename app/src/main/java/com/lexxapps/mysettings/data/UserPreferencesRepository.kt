package com.lexxapps.mysettings.data

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lexxapps.mysettings.utils.titleToData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.Locale

const val APP_SETTINGS = "settings"

private val languageList = listOf("en", "ru", "uk")

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(APP_SETTINGS)

class UserPreferencesRepository(private val context: Context) {

    val userPreferencesFlow: Flow<UserPreference> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapUserPreferences(preferences)
        }

    suspend fun updateLanguage(language: String) {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags("${titleToData(language)}-${Locale.getDefault().country}")
        )
        context.dataStore.edit { preference ->
            preference[PreferencesKeys.LANGUAGE_TAG] = titleToData(language)
        }
    }

    suspend fun updateTheme(theme: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_TAG] = theme
        }
    }

    private fun mapUserPreferences(preferences: Preferences): UserPreference {
        val isDarkTheme = preferences[PreferencesKeys.THEME_TAG] ?: false
        val language = preferences[PreferencesKeys.LANGUAGE_TAG]
            ?: if (languageList.contains(Locale.getDefault().language)) {
                Locale.getDefault().language
            } else {
                "en"
            }

        return UserPreference(
            isDarkTheme = isDarkTheme,
            language = language
        )
    }
}

private object PreferencesKeys {
    val THEME_TAG = booleanPreferencesKey("theme")
    val LANGUAGE_TAG = stringPreferencesKey("language")
}

data class UserPreference(
    val isDarkTheme: Boolean,
    val language: String
)