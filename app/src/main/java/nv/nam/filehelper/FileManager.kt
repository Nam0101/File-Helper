package nv.nam.filehelper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import nv.nam.filehelper.data.FileSource
import nv.nam.filehelper.data.local.LocalFileStorage
import nv.nam.filehelper.data.repository.FileRepositoryImpl
import nv.nam.filehelper.domain.models.FileModel
import nv.nam.filehelper.domain.models.FileType
import nv.nam.filehelper.domain.usecase.GetFileUseCase

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 25/7/2024
 * Github: https://github.com/Nam0101
 * @description : File Manager
 * Provides functionalities to manage files including fetching files of various types and by extension.
 * Utilizes a use case pattern to interact with file storage, supporting both local and potentially remote storage.
 * @property getFileUseCase The use case responsible for fetching files.
 * @property defaultPageSize The default number of files to fetch in a single page.
 */
class FileManager private constructor(
    private val getFileUseCase: GetFileUseCase,
    private val defaultPageSize: Int = 20,
) {
    /**
     * Builder class for [FileManager]. Allows for configuring file source and page size.
     */
    class Builder {
        private var fileSource: FileSource? = null
        private var defaultPageSize: Int = 20

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
         * Builds the FileManager with the configured settings.
         *
         * @return The FileManager instance.
         */
        fun build(): FileManager {
            val fileSource = requireNotNull(fileSource) { "FileSource must be set" }
            val fileRepository = FileRepositoryImpl(fileSource)
            val getFileUseCase = GetFileUseCase(fileRepository)
            return FileManager(getFileUseCase, defaultPageSize)
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
    suspend fun getFileByExtension(
        extension: String,
        page: Int = 1,
        pageSize: Int = defaultPageSize,
        usePagination: Boolean = true
    ): List<FileModel> {
        val files = getAllFiles(page, pageSize, usePagination, FileType.ALL)
        val ext = if (extension.startsWith(".")) extension else ".$extension"
        return files.filter { it.name.endsWith(ext, true) }
    }


    private suspend fun getAllFiles(
        page: Int = 1,
        pageSize: Int = defaultPageSize,
        usePagination: Boolean = true,
        fileType: FileType = FileType.ALL
    ): List<FileModel> {
        return if (usePagination) {
            getFileUseCase.getAllFiles(page, pageSize, fileType).flowOn(Dispatchers.IO).first()
        } else {
            getFileUseCase.getAllFiles(1, Int.MAX_VALUE, fileType).flowOn(Dispatchers.IO).first()
        }
    }
}
