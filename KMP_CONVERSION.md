# Kotlin Multiplatform (KMP) Conversion

This document describes the conversion of the Plaid Link Android project to Kotlin Multiplatform.

## Changes Made

### 1. Build System Conversion
- Converted `build.gradle` to `build.gradle.kts` (Kotlin DSL)
- Converted `settings.gradle` to `settings.gradle.kts`
- Updated root project to support KMP with buildscript dependencies

### 2. Project Structure
- Renamed `app/` to `androidApp/` following KMP conventions
- Created `shared/` module for multiplatform code
- Updated `settings.gradle.kts` to include both modules

### 3. Shared Module Structure
```
shared/
├── src/
│   ├── commonMain/kotlin/
│   │   └── com/plaid/linksample/
│   │       ├── Platform.kt (expect/actual pattern)
│   │       ├── Greeting.kt (demo KMP functionality)
│   │       └── network/
│   │           └── LinkSampleApi.kt (common interface)
│   └── androidMain/kotlin/
│       └── com/plaid/linksample/
│           ├── Platform.android.kt (Android implementation)
│           └── network/
│               └── LinkTokenRequester.kt (Android-specific networking)
└── build.gradle.kts
```

### 4. Dependencies
- **Common**: Minimal dependencies that work across platforms
- **Android**: Plaid SDK, RxJava, Retrofit, Gson (Android-specific networking)
- **iOS**: Placeholder structure (commented out until Xcode is configured)

### 5. Key Features
- **Platform Detection**: `getPlatform()` function demonstrates expect/actual pattern
- **Shared Business Logic**: Common interfaces in `commonMain`
- **Platform-Specific Implementations**: Android-specific code in `androidMain`
- **Network Layer**: Abstracted API interface with platform-specific implementations

## Current Status
- ✅ Android build working
- ✅ Shared module compiling
- ✅ KMP structure in place
- ⏸️ iOS targets disabled (requires Xcode configuration)

## Next Steps
To enable iOS support:
1. Install Xcode and command line tools
2. Uncomment iOS targets in `shared/build.gradle.kts`
3. Implement iOS-specific networking in `shared/src/iosMain/`
4. Create iOS app module

## Usage
```bash
# Build the project
./gradlew build

# Build Android app specifically
./gradlew :androidApp:build

# Build shared module
./gradlew :shared:build
```

## Benefits of KMP Conversion
1. **Code Sharing**: Business logic can be shared between platforms
2. **Type Safety**: Kotlin DSL provides better IDE support
3. **Scalability**: Easy to add new platforms (iOS, Desktop, Web)
4. **Maintainability**: Single source of truth for shared functionality
