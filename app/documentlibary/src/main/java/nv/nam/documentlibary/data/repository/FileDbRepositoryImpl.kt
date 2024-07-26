package nv.nam.documentlibary.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nv.nam.documentlibary.data.db.FileDao
import nv.nam.documentlibary.data.db.toFileModel
import nv.nam.documentlibary.domain.models.FileModel
import nv.nam.documentlibary.domain.repository.FileDbRepository

/**
 * Implementation of the FileDbRepository interface.
 * This class provides methods to interact with the file database.
 *
 * @property fileDao The Data Access Object for file operations.
 */
class FileDbRepositoryImpl(
    private val fileDao: FileDao
) : FileDbRepository {

    /**
     * Retrieves a flow of favorite files from the database.
     *
     * @return A Flow emitting a list of FileModel objects marked as favorite.
     */
    override fun getFavoriteFiles(): Flow<List<FileModel>> = flow {
        fileDao.getFavoriteFiles().collect { files ->
            emit(files.toFileModel())
        }
    }

    /**
     * Marks a file as favorite in the database.
     *
     * @param file The FileModel object representing the file to be marked as favorite.
     */
    override suspend fun addFileToFavorites(file: FileModel) {
        fileDao.addFileToFavorites(file.path)
    }

    /**
     * Retrieves a flow of recent files from the database, limited by the specified number.
     *
     * @param limit The maximum number of recent files to retrieve.
     * @return A Flow emitting a list of FileModel objects representing recent files.
     */
    override fun getRecentFiles(limit: Int): Flow<List<FileModel>> = flow {
        fileDao.getRecentFiles(limit).collect { files ->
            emit(files.toFileModel())
        }
    }

    /**
     * Updates the last accessed date of a file in the database.
     *
     * @param fileModel The FileModel object representing the file to update.
     */
    override suspend fun updateLastAccessedDate(fileModel: FileModel) {
        fileDao.updateLastAccessedDate(fileModel.path)
    }
}