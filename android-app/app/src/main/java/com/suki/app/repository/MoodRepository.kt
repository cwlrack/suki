package com.suki.app.repository

import com.suki.app.data.MoodDao
import com.suki.app.data.MoodEntry
import com.suki.app.data.Mood
import com.suki.app.data.MoodStats
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoodRepository @Inject constructor(
    private val moodDao: MoodDao
) {
    
    // Mood logging
    suspend fun logMood(mood: Mood, note: String? = null, photoPath: String? = null) {
        val moodEntry = MoodEntry(
            mood = mood,
            note = note,
            photoPath = photoPath
        )
        moodDao.insertMood(moodEntry)
    }
    
    suspend fun deleteMood(moodId: Long) {
        moodDao.deleteMood(moodId)
    }
    
    // Mood retrieval
    fun getAllMoods(): Flow<List<MoodEntry>> = moodDao.getAllMoods()
    
    fun getRecentMoods(limit: Int = 7): Flow<List<MoodEntry>> = 
        moodDao.getRecentMoods(limit)
    
    fun getTodaysMoods(): Flow<List<MoodEntry>> = moodDao.getTodaysMoods()
    
    // Analytics
    suspend fun getWeeklyMoodStats(): List<MoodStats> = moodDao.getWeeklyMoodStats()
    
    // Pet state calculation
    suspend fun getCurrentPetState(): PetState {
        val recentMoods = moodDao.getRecentMoods(3)
        
        // Simple implementation - get first few moods and calculate
        var petState = PetState.NEUTRAL
        recentMoods.collect { moods ->
            petState = calculatePetState(moods)
        }
        return petState
    }
    
    private fun calculatePetState(recentMoods: List<MoodEntry>): PetState {
        if (recentMoods.isEmpty()) return PetState.NEUTRAL
        
        // Simple algorithm: weight recent moods more heavily
        val moodCounts = recentMoods.groupingBy { it.mood }.eachCount()
        val dominantMood = moodCounts.maxByOrNull { it.value }?.key ?: Mood.CALM
        
        return when (dominantMood) {
            Mood.HAPPY -> PetState.HAPPY
            Mood.SAD -> PetState.SAD
            Mood.CALM -> PetState.CALM
            Mood.EXCITED -> PetState.EXCITED
            Mood.ANXIOUS -> PetState.ANXIOUS
        }
    }
}

enum class PetState(val emoji: String, val description: String, val backgroundColor: Long) {
    HAPPY("◕‿◕", "Your companion is wagging with joy!", 0xFFFFE5F1),
    SAD("◔_◔", "Your companion needs some gentle care", 0xFFE3F2FD),
    CALM("◡ ◡", "Your companion is peacefully snoozing", 0xFFE8F5E8),
    EXCITED("◕ ◡ ◕", "Your companion is bouncing with energy!", 0xFFFFF8DC),
    ANXIOUS("⊙﹏⊙", "Your companion feels a bit overwhelmed", 0xFFF3E5F5),
    NEUTRAL("ᵔ◡ᵔ", "Your companion is quietly waiting", 0xFFF5F5F5)
}
