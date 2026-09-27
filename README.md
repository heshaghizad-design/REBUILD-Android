# REBUILD Android V1

Personal Android fitness app for Hamed, built in Kotlin + Jetpack Compose.

## Implemented
- Light premium UI
- Persian RTL + English exercise names
- Home dashboard
- 3-day training plan (Saturday / Monday / Wednesday)
- Session A/B/C screens
- Exercise detail screen
- Chest Press in-app animated Start ↔ Press sequence (no external redirect)
- Correct form / common mistake mode
- Chest muscle panel and mistake cards
- Weight / reps / RIR logging persisted locally with SharedPreferences
- Rest timer
- Readiness / neck & left-knee safety screen
- Weekly progress tracking
- Offline-first behavior

## 3D motion asset architecture
The current Chest Press uses local high-fidelity frame assets so the app is usable now.
The same player area is intended to be replaced by pre-rendered 3D MP4 loops in the production build.
Other exercises display a ready-for-motion placeholder until their 3D assets are rendered.

## Build
Open this folder in a current Android Studio installation and let Gradle sync.

Toolchain configuration in this package:
- compileSdk 36
- targetSdk 36
- minSdk 26
- Kotlin 2.2.20
- Android Gradle Plugin 9.0.0
- Gradle 9.1 wrapper configuration

The binary `gradle-wrapper.jar` is not included in this package because this execution environment has no Android SDK/Gradle installation and no network access. Android Studio can regenerate the wrapper or use its configured Gradle distribution.

## Important safety note
The Readiness screen is a training guardrail, not diagnosis or medical clearance.
New weakness, numbness/tingling, radiating neck pain or balance disturbance should stop the workout and prompt medical/physiotherapy assessment.

## Automatic APK build with GitHub Actions

This project now contains `.github/workflows/build-apk.yml`.
After pushing the project to GitHub, open **Actions → Build REBUILD APK → Run workflow**.
When the workflow finishes, download the **REBUILD-debug-apk** artifact. The installable file inside is `app-debug.apk`.

The workflow uses JDK 17, Android API 36 / Build Tools 36.0.0, Gradle 9.1.0, and the project's Android Gradle Plugin 9.0.0.
