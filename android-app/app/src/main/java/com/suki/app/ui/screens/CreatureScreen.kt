package com.suki.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suki.app.data.MoodEntry
import com.suki.app.repository.PetState

@Composable
fun CreatureScreen(
    petState: PetState,
    recentMoods: List<MoodEntry>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        
        // Main pet display with animations
        AnimatedPetDisplay(
            petState = petState,
            modifier = Modifier.padding(32.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Pet status card
        PetStatusCard(
            petState = petState,
            recentMoods = recentMoods
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Mood history visualization
        MoodHistoryVisualization(
            moods = recentMoods.take(7)
        )
    }
}

@Composable
fun AnimatedPetDisplay(
    petState: PetState,
    modifier: Modifier = Modifier
) {
    // Breathing animation
    val infiniteTransition = rememberInfiniteTransition(label = "breathing")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathing_scale"
    )
    
    // State transition animation
    val animatedScale by animateFloatAsState(
        targetValue = if (petState == PetState.EXCITED) 1.2f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "state_scale"
    )
    
    Card(
        modifier = modifier.size(200.dp),
        shape = RoundedCornerShape(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(petState.backgroundColor)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .scale(scale * animatedScale),
            contentAlignment = Alignment.Center
        ) {
            
            // Main pet emoji
            Text(
                text = petState.emoji,
                fontSize = 80.sp,
                modifier = Modifier.animateContentSize()
            )
            
            // Sparkle effect for excited state
            if (petState == PetState.EXCITED) {
                SparkleEffect()
            }
        }
    }
}

@Composable
fun SparkleEffect() {
    val infiniteTransition = rememberInfiniteTransition(label = "sparkles")
    val sparkleAlpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sparkle_alpha"
    )
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        repeat(3) {
            Text(
                text = "✨",
                fontSize = 16.sp,
                modifier = Modifier.alpha(sparkleAlpha)
            )
        }
    }
}

@Composable
fun PetStatusCard(
    petState: PetState,
    recentMoods: List<MoodEntry>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = petState.description,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            if (recentMoods.isNotEmpty()) {
                Text(
                    text = "Based on your recent ${recentMoods.size} mood${if (recentMoods.size > 1) "s" else ""}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun MoodHistoryVisualization(
    moods: List<MoodEntry>
) {
    if (moods.isEmpty()) return
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Recent Mood Journey",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                moods.reversed().forEach { moodEntry ->
                    MoodHistoryItem(moodEntry = moodEntry)
                }
            }
        }
    }
}

@Composable
fun MoodHistoryItem(moodEntry: MoodEntry) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(android.graphics.Color.parseColor(moodEntry.mood.color))
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = moodEntry.mood.emoji,
                    fontSize = 20.sp
                )
            }
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = moodEntry.timestamp.substring(11, 16), // Show time HH:MM
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

// Extension property for pet background colors
private val PetState.backgroundColor: Color
    get() = when (this) {
        PetState.HAPPY -> Color(0xFFFFE5CC)
        PetState.SAD -> Color(0xFFE5F3FF)
        PetState.CALM -> Color(0xFFE8F5E8)
        PetState.EXCITED -> Color(0xFFFFE5F1)
        PetState.ANXIOUS -> Color(0xFFF0E5FF)
        PetState.NEUTRAL -> Color(0xFFF5F5F5)
    }
