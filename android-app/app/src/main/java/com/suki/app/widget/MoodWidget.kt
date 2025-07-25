package com.suki.app.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.material3.*
import androidx.glance.*
import androidx.glance.action.clickable
import androidx.glance.action.actionStartActivity
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.text.FontWeight
import androidx.glance.unit.ColorProvider
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suki.app.MainActivity

class MoodWidget : GlanceAppWidget() {
    
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            MoodWidgetContent()
        }
    }
}

@Composable
fun MoodWidgetContent() {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(ColorProvider(androidx.compose.ui.graphics.Color(0xFFFAF9F7)))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically
    ) {
        
        // Doodle Pet Display - changes based on mood
        Text(
            text = "ᵔ◡ᵔ",  // Simple doodle face inspired by the attachments
            style = TextStyle(
                fontSize = 24.sp,
                color = ColorProvider(androidx.compose.ui.graphics.Color(0xFF2D2D2D)),
                fontWeight = FontWeight.Bold
            )
        )
        
        Spacer(modifier = GlanceModifier.height(8.dp))
        
        // Doodle-style mood buttons
        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Happy - like the first dog image
            MoodButton("◕‿◕", androidx.compose.ui.graphics.Color(0xFFFFB6C1))
            Spacer(modifier = GlanceModifier.width(6.dp))
            
            // Sad - like the simple dog 
            MoodButton("◔_◔", androidx.compose.ui.graphics.Color(0xFFB6E5FF))
            Spacer(modifier = GlanceModifier.width(6.dp))
            
            // Anxious - like the horned character
            MoodButton("⊙﹏⊙", androidx.compose.ui.graphics.Color(0xFFDDA0DD))
            Spacer(modifier = GlanceModifier.width(6.dp))
            
            // Calm - like the pig
            MoodButton("◡ ◡", androidx.compose.ui.graphics.Color(0xFF98FB98))
            Spacer(modifier = GlanceModifier.width(6.dp))
            
            // Excited - like Pikachu
            MoodButton("◕ ◡ ◕", androidx.compose.ui.graphics.Color(0xFFFFD700))
        }
        
        Spacer(modifier = GlanceModifier.height(8.dp))
        
        // Simple tap to open
        Text(
            text = "Tap to see more ♡",
            style = TextStyle(
                fontSize = 10.sp,
                color = ColorProvider(androidx.compose.ui.graphics.Color(0xFF666666))
            ),
            modifier = GlanceModifier.clickable(actionStartActivity<MainActivity>())
        )
    }
}

@Composable
fun MoodButton(face: String, color: androidx.compose.ui.graphics.Color) {
    Text(
        text = face,
        style = TextStyle(
            fontSize = 16.sp,
            color = ColorProvider(androidx.compose.ui.graphics.Color(0xFF2D2D2D)),
            fontWeight = FontWeight.Bold
        ),
        modifier = GlanceModifier
            .background(ColorProvider(color))
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable(actionStartActivity<MainActivity>())
    )
}

class MoodWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = MoodWidget()
}
