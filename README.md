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
     kapt("androidx.room:room-compiler:2.5.2") 
     ```
      - Replace version numbers with the latest versions if needed.

## Usage

### Initializing FileManager

```kotlin
val fileManager = FileManager.Builder()
    .useLocalFileStorage() // Use local storage
    .setDefaultPageSize(50) // Optional: Set max files per page 
    .build()
```

### Accessing Files

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

### Room Database Integration (Optional)

1. **Initialize Database:**

   ```kotlin
   val db = Room.databaseBuilder(context, FileDatabase::class.java, "file_database").build()
   ```

2. **Provide Database to FileManager:**

   ```kotlin
   val fileManager = FileManager.Builder()
       .useLocalFileStorage() 
       .useDatabase(context) // Provide your Room database instance
       .build()
   ```

## Examples

Find example projects and detailed usage instructions in the [examples](/examples) directory.

## Contributing

Contribute to the project! See our [Contributing Guidelines](CONTRIBUTING.md).

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.