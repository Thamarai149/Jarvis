package io.livekit.android.example.voiceassistant.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.livekit.android.compose.types.ReceivedMessage
import io.livekit.android.room.Room

@Composable
fun ChatLog(
    room: Room,
    messages: List<ReceivedMessage>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        reverseLayout = true
    ) {
        items(messages.reversed()) { message ->
            val isFromLocal = message.fromParticipant?.identity == room.localParticipant.identity
            ChatBubble(message.message, isFromLocal)
        }
    }
}

@Composable
fun ChatBubble(message: String, isFromLocal: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = if (isFromLocal) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Surface(
            color = if (isFromLocal) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 2.dp
        ) {
            Text(
                text = message,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                color = if (isFromLocal) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
