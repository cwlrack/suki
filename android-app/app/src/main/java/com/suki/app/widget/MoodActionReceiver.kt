package com.suki.app.widget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.suki.app.data.MoodDatabase
import com.suki.app.data.MoodEntry
import com.suki.app.data.Mood
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoodActionReceiver : BroadcastReceiver() {
    
    companion object {
        const val ACTION_MOOD_SELECTED = "com.suki.app.ACTION_MOOD_SELECTED"
        const val EXTRA_MOOD = "EXTRA_MOOD"
    }
    
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != ACTION_MOOD_SELECTED) return
        
        val moodName = intent.getStringExtra(EXTRA_MOOD) ?: return
        
        try {
            val mood = Mood.valueOf(moodName)
            logMoodToDatabase(context, mood)
            
            // Show quick feedback
            Toast.makeText(
                context,
                "${mood.emoji} Mood logged!",
                Toast.LENGTH_SHORT
            ).show()
            
        } catch (e: IllegalArgumentException) {
            // Invalid mood name
            android.util.Log.e("MoodActionReceiver", "Invalid mood: $moodName", e)
        }
    }
    
    private fun logMoodToDatabase(context: Context, mood: Mood) {
        // Use background scope to avoid blocking UI
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val database = MoodDatabase.getDatabase(context)
                val moodEntry = MoodEntry(mood = mood)
                database.moodDao().insertMood(moodEntry)
                
                // Update widget after mood is logged
                updateWidget(context)
                
            } catch (e: Exception) {
                android.util.Log.e("MoodActionReceiver", "Failed to log mood", e)
            }
        }
    }
    
    private fun updateWidget(context: Context) {
        // Trigger widget update using Glance widget update
        // For now, we'll just show a toast to confirm the mood was logged
        Toast.makeText(context, "Mood logged! Widget will update soon.", Toast.LENGTH_SHORT).show()
    }
}
