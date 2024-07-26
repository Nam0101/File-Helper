package nv.nam.documentlibary

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import nv.nam.documentlibary.data.FileSource
import nv.nam.documentlibary.data.db.FileDatabase
import nv.nam.documentlibary.data.local.LocalFileStorage
import nv.nam.documentlibary.data.repository.FileDbRepositoryImpl
import nv.nam.documentlibary.data.repository.FileRepositoryImpl
import nv.nam.documentlibary.domain.models.FileModel
import nv.nam.documentlibary.domain.models.FileType
import nv.nam.documentlibary.domain.usecase.GetDbFileUseCase
import nv.nam.documentlibary.domain.usecase.GetFileUseCase

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 25/7/2024
 * Github: https://github.com/Nam0101
 * @description
 * Provides functionalities to manage files including fetching files of various types and by extension.
 * Utilizes a use case pattern to interact with file storage, supporting both local and potentially remote storage.
 * @property getFileUseCase The use case responsible for fetching files.
 * @property getDbFileUseCase The use case responsible for fetching files from the database.
 * @property defaultPageSize The default number of files to fetch in a single page.
 */
class FileManager private constructor(
    private val getFileUseCase: GetFileUseCase,
    private val getDbFileUseCase: GetDbFileUseCase?,
    private val context: Context?,
    private val defaultPageSize: Int = 20,
) {
    /**
     * FileManager Builder class.
     *
     * @property getFileUseCase The use case responsible for fetching files.
     * @property defaultPageSize The default number of files to fetch in a single page.
     */
    private constructor(
        getFileUseCase: GetFileUseCase, context: Context, defaultPageSize: Int
    ) : this(getFileUseCase, null, context, defaultPageSize)

    private val dispatcher = Dispatchers.IO


    /**
     * Builder class for [FileManager]. Allows for configuring file source and page size.
     */
    class Builder {
        private var fileSource: FileSource? = null
        private var defaultPageSize: Int = 20

        private var context: Context? = null

        /**
         * Configures the FileManager to use local file storage.
         *
         * @return The Builder instance for chaining.
         */
        fun useLocalFileStorage(): Builder {
            this.fileSource = LocalFileStorage()
            return this
        }

        /**
         * Placeholder for configuring the FileManager to use remote file storage.
         * Currently, this function does not perform any operation.
         *
         * @return The Builder instance for chaining.
         */
        fun useRemoteFileStorage(): Builder {
            // TODO: Implement remote file storage
            return this
        }

        /**
         * Sets the default page size for fetching files.
         *
         * @param pageSize The number of files to fetch in a single page.
         * @return The Builder instance for chaining.
         */
        fun setDefaultPageSize(pageSize: Int): Builder {
            this.defaultPageSize = pageSize
            return this
        }

        /**
         * Sets the default page size to Int.MAX_VALUE, effectively disabling pagination.
         *
         * @return The Builder instance for chaining.
         */
        fun setNotPageSize(): Builder {
            this.defaultPageSize = Int.MAX_VALUE
            return this
        }

        /**
         * Sets the context for the FileManager.
         *
         * @param context The context to use. Should be the application context (use Dagger Hilt or Koin for DI).
         * @return The Builder instance for chaining.
         */
        fun useContext(
            context: Context
        ): Builder {
            this.context = context
            return this
        }

        /**
         * Builds the FileManager with the configured settings.
         *
         * @return The FileManager instance.
         */
        fun build(): FileManager {
            val fileSource = requireNotNull(fileSource) { "FileSource must be set" }
            val fileRepository = FileRepositoryImpl(fileSource)
            val getFileUseCase = GetFileUseCase(fileRepository)
            return if (context != null) {
                val fileDatabase = FileDatabase.getDatabase(context!!)
                val dbFileRepository = FileDbRepositoryImpl(fileDatabase.fileDao())
                val getDbFileUseCase = GetDbFileUseCase(dbFileRepository)
                FileManager(getFileUseCase, getDbFileUseCase, context!!, defaultPageSize)
            } else {
                throw SecurityException("Context must be set to use this class")
            }
        }
    }

    /**
     * Fetches all files of type [FileType.PDF].
     *
     * @param page The page number to fetch.
     * @param pageSize The number of files to fetch in a single page.
     * @param usePagination Flag to enable/disable pagination.
     * @return The list of PDF files.
     */
    suspend fun getAllPdfFiles(
        page: Int = 1, pageSize: Int = defaultPageSize, usePagination: Boolean = true
    ): List<FileModel> = getAllFiles(page, pageSize, usePagination, FileType.PDF)

    /**
     * Fetches all files of type [FileType.IMAGE].
     *
     * @param page The page number to fetch.
     * @param pageSize The number of files to fetch in a single page.
     * @param usePagination Flag to enable/disable pagination.
     * @return The list of image files.
     */
    suspend fun getAllImageFiles(
        page: Int = 1, pageSize: Int = defaultPageSize, usePagination: Boolean = true
    ): List<FileModel> = getAllFiles(page, pageSize, usePagination, FileType.IMAGE)

    /**
     * Fetches all files of type [FileType.VIDEO].
     *
     * @param page The page number to fetch.
     * @param pageSize The number of files to fetch in a single page.
     * @param usePagination Flag to enable/disable pagination.
     * @return The list of video files.
     */
    suspend fun getAllVideoFiles(
        page: Int = 1, pageSize: Int = defaultPageSize, usePagination: Boolean = true
    ): List<FileModel> = getAllFiles(page, pageSize, usePagination, FileType.VIDEO)

    /**
     * Fetches all files of type [FileType.AUDIO].
     *
     * @param page The page number to fetch.
     * @param pageSize The number of files to fetch in a single page.
     * @param usePagination Flag to enable/disable pagination.
     * @return The list of audio files.
     */
    suspend fun getAllAudioFiles(
        page: Int = 1, pageSize: Int = defaultPageSize, usePagination: Boolean = true
    ): List<FileModel> = getAllFiles(page, pageSize, usePagination, FileType.AUDIO)

    /**
     * Fetches all files of type [FileType.DOCUMENT].
     *
     * @param page The page number to fetch.
     * @param pageSize The number of files to fetch in a single page.
     * @param usePagination Flag to enable/disable pagination.
     * @return The list of document files.
     */
    suspend fun getAllDocumentFiles(
        page: Int = 1, pageSize: Int = defaultPageSize, usePagination: Boolean = true
    ): List<FileModel> = getAllFiles(page, pageSize, usePagination, FileType.DOCUMENT)


    /**
     * Fetches all files of type [FileType.ZIP].
     *
     * @param page The page number to fetch.
     * @param pageSize The number of files to fetch in a single page.
     * @param usePagination Flag to enable/disable pagination.
     * @return The list of ZIP files.
     */
    suspend fun getFilesByExtension(
        extension: String,
        page: Int = 1,
        pageSize: Int = defaultPageSize
    ): List<FileModel> = getAllFiles(page, pageSize).filter {
        it.name.endsWith(".${extension}", ignoreCase = true)
    }
    /**
     * Searches for files by name.
     *
     * @param fileName The name of the file to search for.
     * @return The list of files matching the search query.
     */
    suspend fun searchFileByName(fileName: String): List<FileModel> {
        return getFileUseCase.searchFileByName(fileName).flowOn(dispatcher).first()
    }

    /**
     * Fetches the favorite files.
     *
     * @return The list of favorite files.
     */
    suspend fun getFavoriteFiles() =
        getDbFileUseCase?.getFavoriteFiles()?.flowOn(dispatcher)?.first()

    /**
     * Fetches the recent files.
     * @param limit The maximum number of recent files to fetch.
     * @return The list of recent files.
     */
    suspend fun getRecentFiles(limit: Int = 10) =
        getDbFileUseCase?.getRecentFiles(limit)?.flowOn(dispatcher)?.first()

    /**
     * Updates the last accessed date of a file.
     * @param fileModel The file to update.
     */
    suspend fun updateLastAccessedDate(fileModel: FileModel) {
        getDbFileUseCase?.updateLastAccessedDate(fileModel)
    }

    /**
     * Sets a file as a favorite.
     * @param fileModel The file to set as a favorite.
     */
    suspend fun setFavoriteFile(fileModel: FileModel) {
        getDbFileUseCase?.addFileToFavorites(fileModel)
    }

    private fun hasStoragePermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private suspend fun getAllFiles(
        page: Int = 1,
        pageSize: Int = defaultPageSize,
        usePagination: Boolean = true,
        fileType: FileType = FileType.ALL
    ): List<FileModel> {
        if (!context?.let { hasStoragePermission(it) }!!) {
            throw SecurityException(
                "Storage permission not granted. Please request READ_EXTERNAL_STORAGE permission or " + "MANAGE_EXTERNAL_STORAGE permission for Android 11 and above."
            )
        }
        return if (usePagination) {
            getFileUseCase.getAllFiles(page, pageSize, fileType).flowOn(dispatcher).first()
        } else {
            getFileUseCase.getAllFiles(1, Int.MAX_VALUE, fileType).flowOn(dispatcher).first()
        }
    }

    private suspend fun getAllFiles(
        page: Int = 1, pageSize: Int = defaultPageSize, fileType: FileType = FileType.ALL
    ): List<FileModel> {
        return getFileUseCase.getAllFiles(page, pageSize, fileType).flowOn(dispatcher).first()
    }
}
