## FileHelper SDK - README

### Introduction

FileHelper SDK provides a simple and efficient way to manage and access files on Android devices. The SDK supports:

- Fetching a list of all files.
- Filtering files by type (image, video, audio, document, etc.).
- Filtering files by extension.
- Searching for files by name.

### Installation

1. Add the SDK to your project (e.g., by adding a dependency in your `build.gradle`).

2. Initialize `FileManager` with your application's `Context`:

   ```kotlin
   val fileManager = FileManager.Builder()
       .useLocalFileStorage() // Use local storage
       .setDefaultPageSize(50) // Optional: Set the maximum number of files per page
       .build()
   ```

### Usage

**Get a list of all files:**

```kotlin
val allFiles = fileManager.getAllFiles()
```

**Filter files by type:**

```kotlin
val imageFiles = fileManager.getAllImageFiles()
val videoFiles = fileManager.getAllVideoFiles()
val audioFiles = fileManager.getAllAudioFiles()
// ... and other file types
```

**Filter files by extension:**

```kotlin
val pdfFiles = fileManager.getFileByExtension("pdf")
val txtFiles = fileManager.getFileByExtension("txt")
// ... and other extensions
```

**Search for files by name:**

```kotlin
val searchResults = fileManager.searchFileByName("file name to search")
```

**Pagination:**

You can use the `page` and `pageSize` parameters to paginate the returned results:

```kotlin
val firstPage = fileManager.getAllFiles(page = 1, pageSize = 20)
val secondPage = fileManager.getAllFiles(page = 2, pageSize = 20)
```

**Using Room Database:**

If you want to use the SDK's database features (like `GetDbFileUseCase`), you need to initialize the database and provide it to the `FileManager`:

```kotlin
val db = Room.databaseBuilder(context, FileDatabase::class.java, "file_database").build()
val fileManager = FileManager.Builder()
    .useLocalFileStorage() 
    .useDatabase(context) // Initialize database
    .build()
```

### Notes

- The SDK uses coroutines. Make sure you have added coroutines dependencies to your project.
- `FileManager` methods return a `List<FileModel>`, where `FileModel` contains information about the file such as name, path, size, etc.
- The SDK currently only supports accessing files on local storage. Support for accessing files from remote sources (e.g., Google Drive) will be added in the future.

This README provides a basic understanding of the SDK's capabilities. Feel free to explore the provided code and experiment with different functionalities. If you encounter any issues or have further questions, don't hesitate to reach out for support. 
