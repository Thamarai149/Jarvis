package io.livekit.android.example.voiceassistant.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsManager(private val context: Context) {

    companion object {
        val VOICE_KEY = stringPreferencesKey("voice")
        val THEME_KEY = stringPreferencesKey("theme")
        val PTT_MODE_KEY = booleanPreferencesKey("ptt_mode")
        val PERSONALITY_KEY = stringPreferencesKey("personality")
    }

    val voiceFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[VOICE_KEY] ?: "Puck"
    }

    val themeFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: "System"
    }

    val pttModeFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[PTT_MODE_KEY] ?: false
    }

    val personalityFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PERSONALITY_KEY] ?: "Default"
    }

    suspend fun setVoice(voice: String) {
        context.dataStore.edit { it[VOICE_KEY] = voice }
    }

    suspend fun setTheme(theme: String) {
        context.dataStore.edit { it[THEME_KEY] = theme }
    }

    suspend fun setPttMode(enabled: Boolean) {
        context.dataStore.edit { it[PTT_MODE_KEY] = enabled }
    }

    suspend fun setPersonality(personality: String) {
        context.dataStore.edit { it[PERSONALITY_KEY] = personality }
    }
}
