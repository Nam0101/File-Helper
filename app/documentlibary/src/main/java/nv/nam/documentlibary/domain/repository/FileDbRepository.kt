package nv.nam.documentlibary.domain.repository

import kotlinx.coroutines.flow.Flow
import nv.nam.documentlibary.domain.models.FileModel

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 26/7/2024
 * Github: https://github.com/Nam0101
 * @description :
 */
interface FileDbRepository {
    fun getFavoriteFiles(): Flow<List<FileModel>>
    suspend fun addFileToFavorites(file: FileModel)
    fun getRecentFiles(limit: Int): Flow<List<FileModel>>
}