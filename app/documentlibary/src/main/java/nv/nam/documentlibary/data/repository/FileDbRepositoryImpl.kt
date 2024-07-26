package nv.nam.documentlibary.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nv.nam.documentlibary.data.db.FileDao
import nv.nam.documentlibary.data.db.toFileModel
import nv.nam.documentlibary.domain.models.FileModel
import nv.nam.documentlibary.domain.repository.FileDbRepository

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 26/7/2024
 * Github: https://github.com/Nam0101
 * @description :
 */
class FileDbRepositoryImpl
    (
    private val fileDao: FileDao
) : FileDbRepository {
    override fun getFavoriteFiles(): Flow<List<FileModel>> = flow {
        fileDao.getFavoriteFiles().collect { files ->
            emit(files.toFileModel())
        }
    }

    override suspend fun addFileToFavorites(file: FileModel) {
        fileDao.addFileToFavorites(file.path)
    }

    override fun getRecentFiles(limit: Int): Flow<List<FileModel>> = flow {
        fileDao.getRecentFiles(limit).collect { files ->
            emit(files.toFileModel())
        }
    }

    override suspend fun updateLastAccessedDate(fileModel: FileModel) {
        fileDao.updateLastAccessedDate(fileModel.path)
    }
}