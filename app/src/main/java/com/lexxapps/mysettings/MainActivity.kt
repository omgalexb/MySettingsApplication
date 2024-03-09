package com.lexxapps.mysettings

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.lexxapps.mysettings.ui.navigation.MySettingsNavGraph
import com.lexxapps.mysettings.ui.screens.settings.SettingsViewModel
import com.lexxapps.mysettings.ui.theme.MySettingsTheme

class MainActivity : AppCompatActivity() {
    private val viewModel: SettingsViewModel by viewModels(
        factoryProducer = { AppViewModelProvider.factory }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val uiState by viewModel.uiState.collectAsState()
            MySettingsTheme(
                darkTheme = uiState.isDarkTheme
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MySettingsNavGraph()
                }
            }
        }
    }
}