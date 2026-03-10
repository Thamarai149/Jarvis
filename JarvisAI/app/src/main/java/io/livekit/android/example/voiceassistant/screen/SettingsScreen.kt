package io.livekit.android.example.voiceassistant.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.livekit.android.example.voiceassistant.data.SettingsManager
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object SettingsRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val settingsManager = remember { SettingsManager(context) }
    val scope = rememberCoroutineScope()

    val currentVoice by settingsManager.voiceFlow.collectAsState(initial = "Puck")
    val currentTheme by settingsManager.themeFlow.collectAsState(initial = "System")
    val pttMode by settingsManager.pttModeFlow.collectAsState(initial = false)
    val personality by settingsManager.personalityFlow.collectAsState(initial = "Default")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Voice Selection
            SettingsSection(title = "Voice") {
                val voices = listOf("Puck", "Charmer", "Steer", "Dash")
                voices.forEach { voice ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        RadioButton(
                            selected = currentVoice == voice,
                            onClick = { scope.launch { settingsManager.setVoice(voice) } }
                        )
                        Text(text = voice, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }

            // Personality Selection
            SettingsSection(title = "Personality") {
                val personalities = listOf("Default", "Witty", "Professional", "Friendly")
                personalities.forEach { p ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        RadioButton(
                            selected = personality == p,
                            onClick = { scope.launch { settingsManager.setPersonality(p) } }
                        )
                        Text(text = p, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }

            // Theme Selection
            SettingsSection(title = "Theme") {
                val themes = listOf("Light", "Dark", "System")
                themes.forEach { theme ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        RadioButton(
                            selected = currentTheme == theme,
                            onClick = { scope.launch { settingsManager.setTheme(theme) } }
                        )
                        Text(text = theme, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }

            // Push-to-Talk Toggle
            SettingsSection(title = "Push-to-Talk") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Enable PTT Mode")
                    Switch(
                        checked = pttMode,
                        onCheckedChange = { scope.launch { settingsManager.setPttMode(it) } }
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}
