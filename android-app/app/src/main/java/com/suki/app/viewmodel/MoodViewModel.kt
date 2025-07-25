package com.suki.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suki.app.repository.MoodRepository
import com.suki.app.data.MoodEntry
import com.suki.app.data.Mood
import com.suki.app.repository.PetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoodViewModel @Inject constructor(
    private val moodRepository: MoodRepository
) : ViewModel() {
    
    private val _recentMoods = MutableStateFlow<List<MoodEntry>>(emptyList())
    val recentMoods: StateFlow<List<MoodEntry>> = _recentMoods.asStateFlow()
    
    private val _todaysMoods = MutableStateFlow<List<MoodEntry>>(emptyList())
    val todaysMoods: StateFlow<List<MoodEntry>> = _todaysMoods.asStateFlow()
    
    private val _petState = MutableStateFlow(PetState.NEUTRAL)
    val petState: StateFlow<PetState> = _petState.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        observeMoods()
        updatePetState()
    }
    
    private fun observeMoods() {
        viewModelScope.launch {
            moodRepository.getRecentMoods(20).collect { moods ->
                _recentMoods.value = moods
                updatePetState() // Update pet when moods change
            }
        }
        
        viewModelScope.launch {
            moodRepository.getTodaysMoods().collect { moods ->
                _todaysMoods.value = moods
            }
        }
    }
    
    private fun updatePetState() {
        viewModelScope.launch {
            try {
                val state = calculatePetStateFromMoods(_recentMoods.value)
                _petState.value = state
            } catch (e: Exception) {
                // Keep current state on error
            }
        }
    }
    
    private fun calculatePetStateFromMoods(moods: List<MoodEntry>): PetState {
        if (moods.isEmpty()) return PetState.NEUTRAL
        
        // Use most recent mood with some weighted history
        val recentMood = moods.firstOrNull()?.mood ?: return PetState.NEUTRAL
        
        return when (recentMood) {
            Mood.HAPPY -> PetState.HAPPY
            Mood.SAD -> PetState.SAD
            Mood.CALM -> PetState.CALM
            Mood.EXCITED -> PetState.EXCITED
            Mood.ANXIOUS -> PetState.ANXIOUS
        }
    }
    
    fun addMood(mood: Mood, note: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                moodRepository.logMood(mood, note)
                // Moods will update automatically via Flow
            } catch (e: Exception) {
                // Handle error - could emit error state
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun deleteMood(moodId: Long) {
        viewModelScope.launch {
            try {
                moodRepository.deleteMood(moodId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    fun refreshData() {
        observeMoods()
        updatePetState()
    }
}
