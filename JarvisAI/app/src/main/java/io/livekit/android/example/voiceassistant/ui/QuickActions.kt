package io.livekit.android.example.voiceassistant.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class QuickAction(
    val label: String,
    val icon: ImageVector,
    val command: String
)

val quickActions = listOf(
    QuickAction("Weather", Icons.Default.Cloud, "What's the weather like today?"),
    QuickAction("Time", Icons.Default.Schedule, "What time is it?"),
    QuickAction("Joke", Icons.Default.EmojiEmotions, "Tell me a joke"),
    QuickAction("News", Icons.Default.Newspaper, "What's the latest news?"),
    QuickAction("Reminder", Icons.Default.Alarm, "Set a reminder"),
    QuickAction("Calculate", Icons.Default.Calculate, "Help me calculate something"),
    QuickAction("Translate", Icons.Default.Translate, "Translate something for me"),
    QuickAction("Search", Icons.Default.Search, "Search for information"),
    QuickAction("Music", Icons.Default.MusicNote, "Play some music"),
    QuickAction("Email", Icons.Default.Email, "Check my emails"),
    QuickAction("Calendar", Icons.Default.CalendarMonth, "What's on my calendar?"),
    QuickAction("Navigation", Icons.Default.Navigation, "Navigate to a location"),
    QuickAction("Call", Icons.Default.Phone, "Make a phone call"),
    QuickAction("Message", Icons.Default.Message, "Send a message"),
    QuickAction("Timer", Icons.Default.Timer, "Set a timer"),
    QuickAction("Note", Icons.Default.Note, "Take a note"),
    QuickAction("Recipe", Icons.Default.Restaurant, "Find a recipe"),
    QuickAction("Fitness", Icons.Default.FitnessCenter, "Track my workout"),
    QuickAction("Flashlight", Icons.Default.FlashlightOn, "Turn on the flashlight"),
    QuickAction("Selfie", Icons.Default.CameraAlt, "Take a selfie"),
    QuickAction("Music", Icons.Default.MusicNote, "Play my favorite playlist")
)

@Composable
fun QuickActions(
    onActionClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        )
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
            )
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
            ) {
                items(quickActions) { action ->
                    QuickActionButton(
                        action = action,
                        onClick = { onActionClick(action.command) }
                    )
                }
            }
        }
    }
}

@Composable
fun QuickActionButton(
    action: QuickAction,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = action.icon,
                contentDescription = action.label,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = action.label,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
