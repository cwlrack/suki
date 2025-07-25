package com.suki.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suki.app.ui.theme.SukiTheme
import com.suki.app.ui.screens.CreatureScreen
import com.suki.app.viewmodel.MoodViewModel
import com.suki.app.data.MoodEntry
import com.suki.app.data.Mood
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SukiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SukiApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SukiApp(moodViewModel: MoodViewModel = hiltViewModel()) {
    val recentMoods by moodViewModel.recentMoods.collectAsState()
    val todaysMoods by moodViewModel.todaysMoods.collectAsState()
    val petState by moodViewModel.petState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // App Header
        Text(
            text = "Suki",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Creature Screen - The main pet display
        CreatureScreen(
            petState = petState,
            recentMoods = recentMoods,
            modifier = Modifier.weight(1f)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Quick Mood Logging
        QuickMoodSection(onMoodSelected = { mood ->
            moodViewModel.addMood(mood)
        })
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Today's Moods Summary
        if (todaysMoods.isNotEmpty()) {
            TodaysMoodsSummary(moods = todaysMoods)
        }
    }
}

@Composable
fun PetSection(recentMoods: List<MoodEntry>) {
    val petState = calculatePetState(recentMoods)
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(petState.backgroundColor)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = petState.emoji,
                fontSize = 64.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your emotional companion",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun QuickMoodSection(onMoodSelected: (Mood) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "How are you feeling?",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Mood.values().forEach { mood ->
                    MoodButton(
                        mood = mood,
                        onClick = { onMoodSelected(mood) }
                    )
                }
            }
        }
    }
}

@Composable
fun MoodButton(mood: Mood, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(android.graphics.Color.parseColor(mood.color))
        ),
        modifier = Modifier.size(48.dp)
    ) {
        Text(
            text = mood.emoji,
            fontSize = 20.sp
        )
    }
}

@Composable
fun TodaysMoodsSummary(moods: List<MoodEntry>) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "Today's Journey",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                moods.forEach { moodEntry ->
                    Text(
                        text = moodEntry.mood.emoji,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun RecentMoodsSection(moods: List<MoodEntry>) {
    if (moods.isNotEmpty()) {
        Text(
            text = "Recent Entries",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(moods) { moodEntry ->
                MoodEntryCard(moodEntry = moodEntry)
            }
        }
    }
}

@Composable
fun MoodEntryCard(moodEntry: MoodEntry) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = moodEntry.mood.emoji,
                fontSize = 24.sp,
                modifier = Modifier.padding(end = 12.dp)
            )
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = moodEntry.mood.displayName,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = formatTimestamp(moodEntry.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

private fun calculatePetState(recentMoods: List<MoodEntry>): com.suki.app.repository.PetState {
    if (recentMoods.isEmpty()) return com.suki.app.repository.PetState.NEUTRAL
    
    // Simple algorithm: use most recent mood
    return when (recentMoods.first().mood) {
        Mood.HAPPY -> com.suki.app.repository.PetState.HAPPY
        Mood.SAD -> com.suki.app.repository.PetState.SAD
        Mood.CALM -> com.suki.app.repository.PetState.CALM
        Mood.EXCITED -> com.suki.app.repository.PetState.EXCITED
        Mood.ANXIOUS -> com.suki.app.repository.PetState.ANXIOUS
    }
}

private fun formatTimestamp(timestamp: String): String {
    return try {
        val dateTime = LocalDateTime.parse(timestamp)
        dateTime.format(DateTimeFormatter.ofPattern("MMM d, h:mm a"))
    } catch (e: Exception) {
        timestamp
    }
}
