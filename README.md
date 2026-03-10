# JARVIS AI Voice Assistant

An Android voice assistant app powered by LiveKit and Google AI.

## Architecture

```
PC: Python Backend (agent.py) → LiveKit Cloud
                                      ↕
Android: JARVIS App → LiveKit Cloud
```

The Python backend runs on your PC, and the Android app connects to it through LiveKit Cloud.

## Quick Start

### 1. Start Backend on PC

**Install dependencies:**
```cmd
pip install -r requirements.txt
```

**Configure credentials:**

Edit `.env` file with your API keys:
```
GOOGLE_API_KEY=your_google_api_key
LIVEKIT_URL=wss://your-livekit-url.cloud
LIVEKIT_API_KEY=your_api_key
LIVEKIT_API_SECRET=your_api_secret
```

**Start the backend:**
```cmd
python agent.py
```

You should see: "Connecting to LiveKit..."

**Keep this running!**

### 2. Build and Install Android App

**Build the app:**
```cmd
cd JarvisAI
gradlew.bat clean
gradlew.bat installDebug
```

**Or open in Android Studio:**
- Open `JarvisAI` folder
- Click Run ▶️

### 3. Use the App

1. Open JARVIS app on your phone
2. Grant microphone and camera permissions
3. Click "START CALL"
4. Talk to JARVIS!

## Project Structure

```
.
├── agent.py              # Python backend (run on PC)
├── prompt.py             # JARVIS personality configuration
├── .env                  # API credentials
├── requirements.txt      # Python dependencies
└── JarvisAI/            # Android app
    ├── app/
    │   └── src/main/
    │       ├── java/.../
    │       │   ├── MainActivity.kt
    │       │   └── ...
    │       └── AndroidManifest.xml
    └── build.gradle.kts
```

## Requirements

### PC (Backend):
- Python 3.8+
- Internet connection
- API keys (Google, LiveKit)

### Android (App):
- Android 7.0+ (API 24+)
- Microphone and camera permissions
- Internet connection

## Configuration

### Customize JARVIS Personality

Edit `prompt.py`:
```python
AGENT_INSTRUCTION = """Your custom instructions here..."""
AGENT_RESPONSE = """Your custom response here..."""
```

### Update LiveKit Credentials

Edit `.env`:
```
LIVEKIT_URL=wss://your-url.livekit.cloud
LIVEKIT_API_KEY=your_key
LIVEKIT_API_SECRET=your_secret
```

### Update Android App Token

Edit `MainActivity.kt` (line ~150):
```kotlin
val token = "your_new_token_here"
```

## Troubleshooting

### Backend Issues:

**"Module not found" error:**
```cmd
pip install -r requirements.txt
```

**"Connection failed" error:**
- Check internet connection
- Verify LiveKit credentials in `.env`
- Ensure GOOGLE_API_KEY is valid

### Android App Issues:

**App won't install:**
```cmd
adb uninstall io.livekit.android.example.voiceassistant
cd JarvisAI
gradlew.bat installDebug
```

**Permissions denied:**
- Settings → Apps → JARVIS → Permissions
- Grant Microphone, Camera, and Notifications

**Can't connect:**
- Ensure `agent.py` is running on PC
- Check both devices have internet
- Verify LiveKit credentials match

### Build Errors:

**Gradle sync failed:**
```cmd
cd JarvisAI
gradlew.bat clean
gradlew.bat build
```

**SDK not found:**
- Install Android SDK via Android Studio
- Set ANDROID_HOME environment variable

## Development

### Run Backend:
```cmd
python agent.py
```

### View Android Logs:
```cmd
adb logcat | findstr JARVIS
```

### Rebuild App:
```cmd
cd JarvisAI
gradlew.bat clean installDebug
```

## Features

✅ Voice-activated AI assistant
✅ Real-time conversation via LiveKit
✅ Google AI integration
✅ JARVIS personality (Iron Man style)
✅ Session tracking and analytics
✅ Network connectivity monitoring
✅ Permission management

## API Keys Required

1. **Google API Key** - For AI responses
   - Get from: https://makersuite.google.com/app/apikey

2. **LiveKit Credentials** - For voice communication
   - Get from: https://cloud.livekit.io/

## Common Commands

```cmd
# Start backend
python agent.py

# Build Android app
cd JarvisAI
gradlew.bat installDebug

# View Android logs
adb logcat | findstr JARVIS

# Uninstall app
adb uninstall io.livekit.android.example.voiceassistant

# Check connected devices
adb devices
```

## How It Works

1. **Backend (PC)**: Runs `agent.py` which connects to LiveKit and handles AI responses
2. **Android App**: Connects to the same LiveKit room
3. **LiveKit Cloud**: Routes audio between backend and app
4. **Communication**: Real-time voice conversation through LiveKit

Both the backend and app must be connected to the internet and use the same LiveKit credentials.

## Notes

- ⚠️ Backend must be running on PC before using the app
- Both PC and phone need internet connection
- LiveKit handles the voice communication between them
- The app connects to the same LiveKit room as the backend

## License

This is an example project for educational purposes.
