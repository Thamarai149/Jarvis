package io.livekit.android.example.voiceassistant.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_history")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sender: String, // "user" or "agent"
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val sessionId: String
)
