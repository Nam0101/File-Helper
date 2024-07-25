package nv.nam.filehelper.domain.repository

import kotlinx.coroutines.flow.Flow
import nv.nam.filehelper.domain.models.FileModel

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 25/7/2024
 * Github: https://github.com/Nam0101
 * @description : This interface is used to handle file repository use LocalFileStorage and RemoteFileStorage and it will be implemented in FileRepositoryImpl
 */
interface SortAndFilterRepository {
    suspend fun filterFiles(query: String): Flow<List<FileModel>>

    suspend fun orderFiles(orderBy: String): Flow<List<FileModel>>

    suspend fun searchFiles(query: String): Flow<List<FileModel>>
}