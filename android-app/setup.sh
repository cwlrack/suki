#!/bin/bash

# Suki Android App Setup Script
echo "🌸 Setting up Suki Android App..."

# Create necessary directories
echo "📁 Creating missing directories..."
mkdir -p app/src/main/res/mipmap-mdpi
mkdir -p app/src/main/res/mipmap-xhdpi  
mkdir -p app/src/main/res/mipmap-xxhdpi
mkdir -p app/src/main/res/mipmap-xxxhdpi

# Copy icons to all density folders
echo "🎨 Setting up app icons..."
cp app/src/main/res/mipmap-hdpi/ic_launcher.xml app/src/main/res/mipmap-mdpi/
cp app/src/main/res/mipmap-hdpi/ic_launcher.xml app/src/main/res/mipmap-xhdpi/
cp app/src/main/res/mipmap-hdpi/ic_launcher.xml app/src/main/res/mipmap-xxhdpi/
cp app/src/main/res/mipmap-hdpi/ic_launcher.xml app/src/main/res/mipmap-xxxhdpi/

cp app/src/main/res/mipmap-hdpi/ic_launcher_round.xml app/src/main/res/mipmap-mdpi/
cp app/src/main/res/mipmap-hdpi/ic_launcher_round.xml app/src/main/res/mipmap-xhdpi/
cp app/src/main/res/mipmap-hdpi/ic_launcher_round.xml app/src/main/res/mipmap-xxhdpi/
cp app/src/main/res/mipmap-hdpi/ic_launcher_round.xml app/src/main/res/mipmap-xxxhdpi/

echo "✅ Setup complete!"
echo ""
echo "🚀 Next steps:"
echo "1. Open Android Studio"
echo "2. Select 'Open an Existing Project'"
echo "3. Navigate to this folder: $(pwd)"
echo "4. Let Android Studio sync the project"
echo "5. Click 'Run' to build and install Suki!"
echo ""
echo "📱 To test the widget:"
echo "1. Install the app on your device/emulator"
echo "2. Long-press on home screen"
echo "3. Add 'Suki' widget"
echo "4. Tap mood buttons to test one-tap logging!"
echo ""
echo "🌸 Suki is ready to bring emotional awareness to life!"
