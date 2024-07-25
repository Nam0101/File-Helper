package nv.nam.filehelper.data.repository

import kotlinx.coroutines.flow.Flow
import nv.nam.filehelper.data.FileSource
import nv.nam.filehelper.domain.models.FileModel
import nv.nam.filehelper.domain.repository.SortAndFilterRepository

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 25/7/2024
 * Github: https://github.com/Nam0101
 * @description :
 */
class SortAndFilterRepositoryImpl(
    private val dataSource: FileSource,
) : SortAndFilterRepository {
    override suspend fun filterFiles(query: String): Flow<List<FileModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun orderFiles(orderBy: String): Flow<List<FileModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchFiles(query: String): Flow<List<FileModel>> {
        TODO("Not yet implemented")
    }
}