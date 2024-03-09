package com.lexxapps.mysettings.ui.screens.settings

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.lexxapps.mysettings.R
import com.lexxapps.mysettings.ui.navigation.NavigationDestination
import com.lexxapps.mysettings.ui.screens.settings.SettingsDestination.titleRes
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lexxapps.mysettings.AppViewModelProvider
import com.lexxapps.mysettings.utils.dataToTitle

object SettingsDestination : NavigationDestination {
    override val route: String = "settings"
    override val titleRes: Int = R.string.settings_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.factory),
    onNavBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = titleRes)) }, navigationIcon = {
                IconButton(onClick = onNavBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }

            )
        }, modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        SettingsBody(
            modifier = Modifier.padding(paddingValues),
            isDarkTheme = state.isDarkTheme,
            updateTheme = viewModel::updateTheme,
            selectedLanguage = dataToTitle(state.language),
            onLanguageChange = viewModel::updateLanguage
        )
        Log.d("TAG", "${state.language}")
    }
}

@Composable
fun SettingsBody(
    modifier: Modifier,
    isDarkTheme: Boolean,
    updateTheme: (Boolean) -> Unit,
    selectedLanguage: String,
    onLanguageChange: (String) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        SetThemeForm(isDarkTheme = isDarkTheme, updateTheme = updateTheme)
        Divider(modifier = Modifier.padding(horizontal = 8.dp))
        SetLanguageForm(
            selectedLanguage = selectedLanguage, onLanguageChange = onLanguageChange
        )
    }
}

@Composable
fun SetThemeForm(
    isDarkTheme: Boolean, updateTheme: (Boolean) -> Unit
) {
    val iconRes: Int =
        if (isDarkTheme) R.drawable.baseline_mode_night_24 else R.drawable.baseline_light_mode_24
    val subtitleRes: Int = if (isDarkTheme) R.string.dark_theme else R.string.light_theme

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SettingsIcon(painter = painterResource(id = iconRes))
        Column(
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        ) {
            SettingsTitle(title = stringResource(R.string.theme))
            SettingsSubtitle(subtitle = stringResource(id = subtitleRes))
        }
        Switch(checked = isDarkTheme, onCheckedChange = {
            updateTheme(it)
        })
    }
}

@Composable
fun SetLanguageForm(
    selectedLanguage: String, onLanguageChange: (String) -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                openDialog = true
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        SettingsIcon(painter = painterResource(id = R.drawable.baseline_language_24))
        Column(
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        ) {
            SettingsTitle(title = stringResource(R.string.language))
            SettingsSubtitle(subtitle = selectedLanguage)
        }
    }

    SetLanguageDialog(
        openDialog = openDialog,
        onDismiss = { openDialog = false },
        onLanguageChange = onLanguageChange,
        selectedLanguage = selectedLanguage
    )
}

@Composable
fun SetLanguageDialog(
    openDialog: Boolean,
    onDismiss: () -> Unit,
    onLanguageChange: (String) -> Unit,
    selectedLanguage: String
) {
    val languageOptions = listOf("English", "Русский", "Українська")
    if (openDialog) {
        AlertDialog(onDismissRequest = onDismiss, confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(stringResource(R.string.cancel_button))
            }
        }, text = {
            LazyColumn() {
                items(
                    items = languageOptions,
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (it == selectedLanguage), onClick = {
                                    onLanguageChange(it)
                                    onDismiss()
                                }, role = Role.RadioButton
                            )
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        RadioButton(
                            selected = (it == selectedLanguage), onClick = null
                        )
                    }
                }
            }
        }, modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.small
        )
    }
}

@Composable
fun SettingsIcon(modifier: Modifier = Modifier, painter: Painter) {
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier.size(40.dp, 40.dp),
        contentScale = ContentScale.Crop,
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun SettingsTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        maxLines = 1,
        modifier = modifier
    )
}

@Composable
fun SettingsSubtitle(subtitle: String) {
    Text(
        text = subtitle,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )
}