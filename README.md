# Interprocess Communication in Android
This repository contains the code and supporting files for demonstrating interprocess communication in Android. The following apps are ordered in increasing levels of complexity for different IPC techniques:

- MyEmail + MyNotepad
  - Simple IPC via shared directory in the sdcard
- MyEmailBinder + MyNotepadBinder
  - Implementing IPC via the Android Binder
- MyEmailNotif + MyNotepadNotif
  - Android Binder communication with additional DeathRecipient attached from the notepad app

## Android Source Code References
- Binder
  - https://cs.android.com/android/kernel/superproject/+/common-android-mainline:common/drivers/android/binder.c 
- ServiceManager
  - https://cs.android.com/android/platform/superproject/+/master:frameworks/base/core/java/android/os/ServiceManager.java
