package nv.nam.documentlibary.domain.usecase

import nv.nam.documentlibary.data.repository.FileDbRepositoryImpl
import nv.nam.documentlibary.domain.models.FileModel

/**
 * Use case class for database file operations.
 * Provides methods to interact with the file database repository.
 *
 * @property fileDbRepository The repository implementation for file database operations.
 *
 * @constructor Creates a GetDbFileUseCase with the specified repository.
 *
 * @param fileDbRepository The repository implementation for file database operations.
 *
 * Project: File Helper
 * Created: 26/7/2024
 * Github: https://github.com/Nam0101
 *
 * @description :
 */
class GetDbFileUseCase(
    private val fileDbRepository: FileDbRepositoryImpl
) {

    /**
     * Retrieves a flow of favorite files from the database.
     *
     * @return A Flow emitting a list of FileModel objects marked as favorite.
     */
    fun getFavoriteFiles() = fileDbRepository.getFavoriteFiles()

    /**
     * Retrieves a flow of recent files from the database, limited by the specified number.
     *
     * @param limit The maximum number of recent files to retrieve.
     * @return A Flow emitting a list of FileModel objects representing recent files.
     */
    fun getRecentFiles(limit: Int) = fileDbRepository.getRecentFiles(limit)

    /**
     * Marks a file as favorite in the database.
     *
     * @param fileModel The FileModel object representing the file to be marked as favorite.
     */
    suspend fun addFileToFavorites(fileModel: FileModel) =
        fileDbRepository.addFileToFavorites(fileModel)

    /**
     * Updates the last accessed date of a file in the database.
     *
     * @param fileModel The FileModel object representing the file to update.
     */
    suspend fun updateLastAccessedDate(fileModel: FileModel) {
        fileDbRepository.updateLastAccessedDate(fileModel)
    }
}