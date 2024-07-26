package nv.nam.documentlibary.domain.repository

import kotlinx.coroutines.flow.Flow
import nv.nam.documentlibary.domain.models.FileModel

/**
 * Interface representing the repository for file database operations.
 * Provides methods to interact with the file database.
 *
 * @author Nam
 * Project: File Helper
 * Created: 26/7/2024
 * Github: https://github.com/Nam0101
 * @description :
 */
interface FileDbRepository {

    /**
     * Retrieves a flow of favorite files from the database.
     *
     * @return A Flow emitting a list of FileModel objects marked as favorite.
     */
    fun getFavoriteFiles(): Flow<List<FileModel>>

    /**
     * Marks a file as favorite in the database.
     *
     * @param file The FileModel object representing the file to be marked as favorite.
     */
    suspend fun addFileToFavorites(file: FileModel)

    /**
     * Retrieves a flow of recent files from the database, limited by the specified number.
     *
     * @param limit The maximum number of recent files to retrieve.
     * @return A Flow emitting a list of FileModel objects representing recent files.
     */
    fun getRecentFiles(limit: Int): Flow<List<FileModel>>

    /**
     * Updates the last accessed date of a file in the database.
     *
     * @param fileModel The FileModel object representing the file to update.
     */
    suspend fun updateLastAccessedDate(fileModel: FileModel)
}