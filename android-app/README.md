# Suki Android App

**Suki** is an emotional companion app that turns mood tracking into caring for a digital pet. The breakthrough innovation is **widget-first design** - users can log moods with one tap directly from their home screen, making emotional awareness as effortless as checking the time.

## 🎯 Core Innovation

**Widget-First Mood Logging:**
- One-tap mood logging directly from home screen widget
- No app opening required
- Pet responds instantly to mood changes
- Zero friction = sustainable habits

## 🏗️ Architecture

**Modern Android Stack:**
- **Kotlin** + **Jetpack Compose** - Modern UI
- **Room Database** - Local mood storage
- **Hilt/Dagger** - Dependency injection
- **Glance** - Modern widget framework
- **MVVM Pattern** - Clean architecture

**Key Components:**
- `MoodWidget.kt` - Home screen widget with one-tap logging
- `MoodRepository.kt` - Data layer with clean separation
- `CreatureScreen.kt` - Animated pet companion display
- `MoodDatabase.kt` - Room database for mood persistence

## 🚀 Getting Started

### Prerequisites
- **Android Studio** (latest version)
- **JDK 8+**
- **Android SDK** (API 24+)

### Setup Instructions

1. **Clone and Open:**
   ```bash
   git clone <your-repo>
   cd suki/android-app
   ```

2. **Open in Android Studio:**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to `suki/android-app` folder
   - Click "Open"

3. **Sync Project:**
   - Android Studio will automatically sync Gradle
   - Wait for build to complete

4. **Run the App:**
   - Connect device or start emulator
   - Click "Run" button in Android Studio
   - App will install and launch

5. **Test Widget:**
   - Add widget to home screen
   - Tap mood buttons to test one-tap logging
   - Open app to see pet respond to moods

## 📱 Key Features

### Widget-First Design
- **Five mood buttons** (😊😢😌🤩😰) on home screen
- **Instant logging** with immediate pet response
- **Always visible** emotional companion

### Animated Pet Companion
- **Breathing animations** for lifelike feel
- **State transitions** based on recent moods
- **Sparkle effects** for excited states
- **Warm, colorful backgrounds**

### Clean Data Architecture
- **Room database** for reliable local storage
- **Repository pattern** for clean data access
- **Hilt injection** for proper dependency management
- **Flow-based** reactive UI updates

## 🔧 Development Notes

### Build Configuration
- **Gradle 8.2** with Android plugin 8.2.1
- **Kotlin 1.9.22** with Compose 1.5.8
- **Hilt 2.48** for dependency injection
- **Target SDK 34** with minimum SDK 24

### Project Structure
```
app/src/main/java/com/suki/app/
├── data/              # Room database, entities, DAOs
├── di/                # Hilt dependency injection modules
├── repository/        # Data repository with business logic
├── ui/screens/        # Compose UI screens
├── ui/theme/          # Material 3 theming
├── viewmodel/         # ViewModels with StateFlow
├── widget/            # Home screen widget components
├── MainActivity.kt    # Main app entry point
└── SukiApplication.kt # Application class with Hilt
```

### Testing the Widget
1. Build and install app
2. Long-press home screen
3. Add "Suki" widget
4. Tap mood emoji buttons
5. Observe toast confirmation
6. Open app to see pet state changes

## 🎨 Design Philosophy

**Radical Simplicity:**
- Mood logging in one tap
- No navigation required
- Unconscious habit formation
- Immediate visual feedback

**Emotional Connection:**
- Warm, friendly pet companion
- Beautiful animations and colors
- Non-clinical approach to wellness
- Privacy-first, local data storage

## 🔮 Next Steps

1. **Test Core Flow:**
   - Widget → mood logging → database → pet updates

2. **Add Polish:**
   - Custom pet animations
   - Sound effects
   - Haptic feedback

3. **Enhance Features:**
   - Photo attachments
   - Weather integration
   - Mood analytics

## 📝 Known Issues

- **Gradle wrapper**: May need manual Android Studio sync
- **Widget positioning**: Varies by launcher
- **Emulator widgets**: Test on real device for best experience

## 🎯 Success Metrics

**Technical:**
- ✅ Widget buttons save to database
- ✅ Pet state updates based on moods  
- ✅ App displays mood history
- ✅ Clean architecture with DI

**User Experience:**
- One-tap logging works smoothly
- Pet feels alive and responsive
- Emotional attachment to companion
- Daily usage without pressure

---

**Suki** represents a breakthrough in emotional wellness apps by making mood tracking so simple it becomes unconscious. The widget-first design eliminates the primary barrier that kills most mood tracking: having to remember to open an app. 🌸
