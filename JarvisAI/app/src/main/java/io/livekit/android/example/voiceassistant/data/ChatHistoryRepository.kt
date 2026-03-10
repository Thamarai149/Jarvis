package io.livekit.android.example.voiceassistant.data

import kotlinx.coroutines.flow.Flow

class ChatHistoryRepository(private val chatHistoryDao: ChatHistoryDao) {
    
    suspend fun saveMessage(sender: String, content: String, sessionId: String) {
        val message = ChatMessageEntity(
            sender = sender,
            content = content,
            sessionId = sessionId
        )
        chatHistoryDao.insertMessage(message)
    }

    fun getMessagesForSession(sessionId: String): Flow<List<ChatMessageEntity>> =
        chatHistoryDao.getMessagesForSession(sessionId)

    fun getAllSessions(): Flow<List<String>> =
        chatHistoryDao.getAllSessions()

    suspend fun clearHistory() {
        chatHistoryDao.clearAllHistory()
    }
}
