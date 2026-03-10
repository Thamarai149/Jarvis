package io.livekit.android.example.voiceassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import io.livekit.android.example.voiceassistant.screen.*
import io.livekit.android.example.voiceassistant.data.SettingsManager
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import io.livekit.android.LiveKit
import io.livekit.android.example.voiceassistant.screen.ConnectRoute
import io.livekit.android.example.voiceassistant.screen.ConnectScreen
import io.livekit.android.example.voiceassistant.screen.VoiceAssistantRoute
import io.livekit.android.example.voiceassistant.screen.VoiceAssistantScreen
import io.livekit.android.example.voiceassistant.ui.theme.LiveKitVoiceAssistantExampleTheme
import io.livekit.android.example.voiceassistant.viewmodel.VoiceAssistantViewModel
import io.livekit.android.util.LoggingLevel

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LiveKit.loggingLevel = LoggingLevel.DEBUG

        setContent {
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }

            LiveKitVoiceAssistantExampleTheme(dynamicColor = false) {
                val context = LocalContext.current
                val settingsManager = remember { SettingsManager(context) }
                val currentTheme by settingsManager.themeFlow.collectAsState(initial = "System")

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text("JARVIS") },
                            actions = {
                                IconButton(onClick = { navController.navigate(SettingsRoute) }) {
                                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {

                        NavHost(
                            navController = navController,
                            startDestination = ConnectRoute
                        ) {

                            composable<ConnectRoute> {
                                ConnectScreen {
                                    val url = "wss://jarvis-e50u3myd.livekit.cloud"
                                    val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NzAzNzUxNTksImlkZW50aXR5IjoidGVzdF91c2VyIiwiaXNzIjoiQVBJZ0x5VFpDTjdBWmFtIiwibmFtZSI6InRlc3RfdXNlciIsIm5iZiI6MTc3MDI4ODc1OSwic3ViIjoidGVzdF91c2VyIiwidmlkZW8iOnsicm9vbSI6InRlc3Rfcm9vbSIsInJvb21Kb2luIjp0cnVlfX0.hUcGVn6T4JhBNJMGig5m1brOdVqHLYm3BaebSIgKjhE"

                                    navController.navigate(
                                        VoiceAssistantRoute(
                                            hardcodedUrl = url,
                                            hardcodedToken = token,
                                            sandboxId = "jarvis2026-1y3ui7"
                                        )
                                    )
                                }
                            }

                            composable<VoiceAssistantRoute> {
                                val viewModel = viewModel<VoiceAssistantViewModel>()

                                VoiceAssistantScreen(
                                    viewModel = viewModel,
                                    onEndCall = {
                                        navController.navigateUp()
                                    }
                                )
                            }

                            composable<SettingsRoute> {
                                SettingsScreen(onBack = { navController.navigateUp() })
                            }
                        }
                    }
                }
            }
        }
    }
}
