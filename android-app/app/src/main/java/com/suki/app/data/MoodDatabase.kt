package com.suki.app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "mood_entries")
data class MoodEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val mood: Mood,
    val timestamp: String = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
    val note: String? = null,
    val photoPath: String? = null,
    val weather: String? = null,
    val location: String? = null
)

enum class Mood(val displayName: String, val emoji: String, val color: String) {
    HAPPY("Happy", "◕‿◕", "#FFB6C1"),
    SAD("Sad", "◔_◔", "#B6E5FF"), 
    CALM("Calm", "◡ ◡", "#98FB98"),
    EXCITED("Excited", "◕ ◡ ◕", "#FFD700"),
    ANXIOUS("Anxious", "⊙﹏⊙", "#DDA0DD")
}

@Dao
interface MoodDao {
    @Query("SELECT * FROM mood_entries ORDER BY timestamp DESC")
    fun getAllMoods(): Flow<List<MoodEntry>>
    
    @Query("SELECT * FROM mood_entries ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentMoods(limit: Int = 7): Flow<List<MoodEntry>>
    
    @Query("SELECT * FROM mood_entries WHERE DATE(timestamp) = DATE('now') ORDER BY timestamp DESC")
    fun getTodaysMoods(): Flow<List<MoodEntry>>
    
    @Insert
    suspend fun insertMood(mood: MoodEntry)
    
    @Query("DELETE FROM mood_entries WHERE id = :id")
    suspend fun deleteMood(id: Long)
    
    @Query("SELECT mood, COUNT(*) as count FROM mood_entries WHERE timestamp >= datetime('now', '-7 days') GROUP BY mood")
    suspend fun getWeeklyMoodStats(): List<MoodStats>
}

data class MoodStats(
    val mood: Mood,
    val count: Int
)

@Database(
    entities = [MoodEntry::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MoodDatabase : RoomDatabase() {
    abstract fun moodDao(): MoodDao
    
    companion object {
        @Volatile
        private var INSTANCE: MoodDatabase? = null
        
        fun getDatabase(context: android.content.Context): MoodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    MoodDatabase::class.java,
                    "mood_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class Converters {
    @TypeConverter
    fun fromMood(mood: Mood): String = mood.name
    
    @TypeConverter
    fun toMood(moodName: String): Mood = Mood.valueOf(moodName)
}
