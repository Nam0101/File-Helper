# FileHelper SDK - README

## Introduction

The FileHelper SDK provides a simple and efficient way to manage and access files on Android devices. It simplifies file operations such as fetching, filtering, searching, and pagination. The SDK also offers optional integration with Room Database for persistent file management.

## Key Features

- **File Listing:** Fetch a list of all files on the device.
- **Filtering:**
  - Filter files by type (image, video, audio, document, etc.).
  - Filter files by file extension.
- **Searching:** Search for files by name.
- **Pagination:** Easily handle large file lists with pagination support.
- **Room Database Integration (Optional):** Enables persistent storage of file-related data using Room Database.

## Installation

1. **Add the SDK dependency:**

   - Add the following line to your module's `build.gradle.kts` (or `build.gradle`) file, replacing `1.0.0` with the latest SDK version:

     ```gradle
     implementation("nv.nam:filehelper:1.0.0")
     ```

   - Sync your project with Gradle files.

2. **(Optional) Add Room Database Dependency (if needed):**

   - Add the following dependencies to your `build.gradle.kts` (or `build.gradle`) file:

     ```gradle
     implementation("androidx.room:room-runtime:2.5.2")
     implementation("androidx.room:room-ktx:2.5.2")
     ksp("androidx.room:room-compiler:2.5.2")
     ```

     - Replace version numbers with the latest versions if needed.

## Usage

### Permissions

The FileHelper SDK requires storage permission to access files on the device. 

**Android 6.0 (API level 23) and above:**

- Add the `READ_EXTERNAL_STORAGE` permission to your app's `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

- Request the permission at runtime:

```kotlin
if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_STORAGE_PERMISSION)
}
```

**Android 11 (API level 30) and above:**

- If your app needs access to all files on the device, you also need to request the `MANAGE_EXTERNAL_STORAGE` permission. This permission can only be granted through a system settings page. You can direct users to the settings page using an intent:

```kotlin
val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
intent.data = Uri.parse("package:" + context.packageName)
startActivity(intent)
```

### Initializing FileManager

```kotlin
val fileManager = FileManager.Builder()
    .useLocalFileStorage() // Use local storage
    .useContext(context) // Context
    .setDefaultPageSize(50) // Optional: Set max files per page
    .build()
```

### Or in a better way you can use DI with koin

```kotlin
single<FileManager> {
   FileManager.Builder().useLocalFileStorage().useContext(
      context = get(),
   ).setNotPageSize().build()
}

```

### Accessing Files
The FileHelper SDK will throw a `SecurityException` if the necessary storage permissions are not granted. Make sure to request and handle permissions before calling any file access methods.

```kotlin
// Get all files
val allFiles = fileManager.getAllFiles()

// Filter by type (image, video, audio, etc.)
val imageFiles = fileManager.getAllImageFiles()

// Filter by extension
val pdfFiles = fileManager.getFileByExtension("pdf")

// Search by name
val results = fileManager.searchFileByName("report.pdf")

// Pagination
val firstPage = fileManager.getAllFiles(page = 1, pageSize = 20)
```

## Examples

Find example projects and detailed usage instructions in the [examples](/app/src/main/java/nv/nam/filehelper/) directory.

## Contributing

Contribute to the project! See our [Contributing Guidelines](CONTRIBUTING.md).

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.
