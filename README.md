```markdown
# FileHelper SDK - README

## Introduction

The FileHelper SDK provides a simple and efficient way to manage and access files on Android devices. 

## Features

- Fetching a list of all files.
- Filtering files by type (image, video, audio, document, etc.).
- Filtering files by extension.
- Searching for files by name.
- Pagination support for handling large file lists.
- Integration with Room Database for persistent file management (optional).

## Installation

1. **Add the SDK dependency:**

   - Add the following line to your module's `build.gradle.kts` (or `build.gradle`) file:

     ```gradle
     implementation("nv.nam:filehelper:1.0.0") // Replace with the actual artifact details
     ```

   - Replace `1.0.0` with the latest version of the FileHelper SDK.

2. **Sync Project:**

   - Sync your project with Gradle files. 

## Basic Usage

1. **Initialize `FileManager`:**

   ```kotlin
   val fileManager = FileManager.Builder()
       .useLocalFileStorage() // Use local storage
       .setDefaultPageSize(50) // Optional: Set max files per page 
       .build()
   ```

2. **Access Files:**

   ```kotlin
   // Get all files
   val allFiles = fileManager.getAllFiles()

   // Filter by type
   val imageFiles = fileManager.getAllImageFiles() 

   // Filter by extension
   val pdfFiles = fileManager.getFileByExtension("pdf")

   // Search by name
   val results = fileManager.searchFileByName("report.pdf") 

   // Pagination
   val firstPage = fileManager.getAllFiles(page = 1, pageSize = 20) 
   ```

## Room Database Integration (Optional)

For persistent storage and more advanced file management, you can integrate FileHelper with Room Database.

1. **Add Room Dependency:**

   ```gradle
   implementation("androidx.room:room-runtime:2.5.2") // Replace with the latest version
   implementation("androidx.room:room-ktx:2.5.2") 
   kapt("androidx.room:room-compiler:2.5.2") 
   ```

2. **Initialize Database:**

   ```kotlin
   val db = Room.databaseBuilder(context, FileDatabase::class.java, "file_database").build()
   ```

3. **Provide Database to FileManager:**

   ```kotlin
   val fileManager = FileManager.Builder()
       .useLocalFileStorage() 
       .useDatabase(context) // Provide your initialized Room database 
       .build()
   ```

## Notes

- The FileHelper SDK uses Kotlin Coroutines for asynchronous operations. Make sure you have added the necessary coroutines dependencies to your project.
- The `FileModel` class represents file information and provides properties like `name`, `path`, `size`, and more.

## Examples

You can find example projects and more detailed usage instructions in the [examples](/examples) directory.

## Contributing

We welcome contributions! Please see our [Contributing Guidelines](CONTRIBUTING.md) for more information.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.
```