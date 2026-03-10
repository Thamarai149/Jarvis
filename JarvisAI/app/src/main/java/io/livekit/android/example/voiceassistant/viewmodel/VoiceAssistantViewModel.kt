package io.livekit.android.example.voiceassistant.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import io.livekit.android.LiveKit
import io.livekit.android.example.voiceassistant.screen.VoiceAssistantRoute
import io.livekit.android.token.TokenSource
import io.livekit.android.token.cached
import io.livekit.android.example.voiceassistant.data.ChatHistoryRepository
import io.livekit.android.example.voiceassistant.data.JarvisDatabase
import io.livekit.android.room.Room
import io.livekit.android.room.track.Track
import io.livekit.android.room.participant.Participant
import androidx.lifecycle.viewModelScope
import io.livekit.android.events.RoomEvent
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*
import java.util.UUID

/**
 * This ViewModel handles holding onto the Room object, so that it is
 * maintained across configuration changes, such as rotation.
 */
class VoiceAssistantViewModel(application: Application, savedStateHandle: SavedStateHandle) : AndroidViewModel(application) {

    val room = LiveKit.create(application)

    val tokenSource: TokenSource
    private val repository: ChatHistoryRepository
    val sessionId = UUID.randomUUID().toString()

    init {
        val (sandboxId, url, token) = savedStateHandle.toRoute<VoiceAssistantRoute>()

        tokenSource = if (sandboxId.isNotEmpty()) {
            TokenSource.fromSandboxTokenServer(sandboxId = sandboxId).cached()
        } else {
            TokenSource.fromLiteral(url, token).cached()
        }

        val database = JarvisDatabase.getDatabase(application)
        repository = ChatHistoryRepository(database.chatHistoryDao())

        setupMessageListener()
    }

    private fun setupMessageListener() {
        viewModelScope.launch {
            room.events.collect { event ->
                if (event is RoomEvent.ParticipantMetadataChanged) {
                    val participant = event.participant
                    repository.saveMessage(
                        sender = if (participant != null && participant.identity != room.localParticipant.identity) "agent" else "user",
                        content = participant.metadata ?: "",
                        sessionId = sessionId
                    )
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        room.disconnect()
        room.release()
    }
}
