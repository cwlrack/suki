#!/bin/bash

# Suki App Widget Test Script
echo "🌸 Building and installing Suki with doodle theme widget..."

# Build the app
echo "Building APK..."
cd /Users/emirate/suki/android-app
./gradlew assembleDebug

if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo ""
    echo "🎨 Doodle theme features implemented:"
    echo "  • Doodle character faces: ◕‿◕ ◔_◔ ⊙﹏⊙ ◡ ◡ ◕ ◡ ◕"
    echo "  • Soft, warm color palette inspired by your mascot images"
    echo "  • Simple dog character launcher icon"
    echo "  • Widget loading issues fixed with Glance 1.1.1"
    echo ""
    echo "📱 To test the widget:"
    echo "  1. Install: adb install app/build/outputs/apk/debug/app-debug.apk"
    echo "  2. Add widget to home screen"
    echo "  3. Tap doodle mood faces to log emotions"
    echo "  4. Watch your pet companion react ᵔ◡ᵔ"
    echo ""
    echo "🐕 APK ready at: app/build/outputs/apk/debug/app-debug.apk"
    echo "📄 Size: $(du -h app/build/outputs/apk/debug/app-debug.apk | cut -f1)"
else
    echo "❌ Build failed. Check the errors above."
    exit 1
fi
