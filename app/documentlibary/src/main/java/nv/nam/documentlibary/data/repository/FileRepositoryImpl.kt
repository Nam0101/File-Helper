package nv.nam.documentlibary.data.repository

import kotlinx.coroutines.flow.Flow
import nv.nam.documentlibary.data.FileSource
import nv.nam.documentlibary.domain.models.FileModel
import nv.nam.documentlibary.domain.models.FileType
import nv.nam.documentlibary.domain.repository.FileRepository

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 25/7/2024
 * Github: https://github.com/Nam0101
 * @description :
 */
class FileRepositoryImpl(
    private val dataSource: FileSource
) : FileRepository {
    override suspend fun getAllFiles(page: Int, pageSize: Int, fileType: FileType): Flow<List<FileModel>> {
        return dataSource.getAllFiles(page, pageSize, fileType)
    }

    override suspend fun searchFileByName(fileName: String): Flow<List<FileModel>> {
        return dataSource.searchFileByName(fileName)
    }
}