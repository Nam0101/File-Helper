package nv.nam.documentlibary.data.local

import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import nv.nam.documentlibary.data.FileSource
import nv.nam.documentlibary.domain.models.FileModel
import nv.nam.documentlibary.domain.models.FileType
import nv.nam.documentlibary.domain.models.toFileModel
import java.io.File
import kotlin.coroutines.CoroutineContext

/**
 * LocalFileStorage is a class that handles local file storage operations.
 * It implements the FileSource interface to provide functionalities for fetching and searching files.
 *
 * @author Nam Nguyen Van
 * @project File Helper
 * @created 25/7/2024
 * @github https://github.com/Nam0101
 */
class LocalFileStorage : FileSource {
    private val localStorageDirectory = "/storage/emulated/0"
    private val TAG = LocalFileStorage::class.java.simpleName
    private var cachedFiles: List<FileModel>? = null
    private var updateJob: Job? = null
    private val contextDispatcher: CoroutineContext = Dispatchers.IO + Job()

    /**
     * Fetches all files from the local storage based on the provided page, page size, and file type.
     * File list is cached to avoid redundant file system access.
     *
     * @param page The page number to fetch.
     * @param pageSize The number of files per page.
     * @param fileType The type of files to fetch.
     * @return A Flow emitting a list of FileModel objects.
     */
    override suspend fun getAllFiles(
        page: Int, pageSize: Int, fileType: FileType
    ): Flow<List<FileModel>> = flow {
        cachedFiles?.let {
            emit(it.chunked(pageSize).getOrNull(page - 1) ?: emptyList())
        }

        if (updateJob?.isActive == true) {
            updateJob?.join()
        }
        updateJob = Job()
        withContext(contextDispatcher) {
            cachedFiles = getFilesFromInternalStorage(fileType = fileType)
        }
        (updateJob as CompletableJob).complete()
        emit(cachedFiles?.chunked(pageSize)?.getOrNull(page - 1) ?: emptyList())
    }

    /**
     * Searches for files by name in the local storage.
     *
     * @param fileName The name of the file to search for.
     * @return A Flow emitting a list of FileModel objects that match the search criteria.
     */
    override suspend fun searchFileByName(fileName: String): Flow<List<FileModel>> = flow {
        if (cachedFiles.isNullOrEmpty()) {
            cachedFiles = getFilesFromInternalStorage()
        }
        emit(cachedFiles?.filter { it.name.contains(fileName, ignoreCase = true) } ?: emptyList())
    }

    /**
     * Retrieves files from the internal storage based on the provided directory path and file type.
     *
     * @param directoryPath The path of the directory to search in.
     * @param fileType The type of files to retrieve.
     * @return A list of FileModel objects.
     */
    private fun getFilesFromInternalStorage(
        directoryPath: String = localStorageDirectory, fileType: FileType = FileType.ALL
    ): List<FileModel> = sequence {
        val directory = File(directoryPath)
        if (directory.exists() && directory.isDirectory) {
            directory.listFiles()?.forEach { file ->
                if (file.isFile) {
                    if (fileType == FileType.ALL || file.extension.lowercase() in fileType.extensions) {
                        yield(file.toFileModel())
                    }
                } else if (file.isDirectory) {
                    yieldAll(getFilesFromInternalStorage(file.absolutePath, fileType))
                }
            }
        }
    }.toList()
}