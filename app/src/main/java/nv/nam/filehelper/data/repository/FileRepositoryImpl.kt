package nv.nam.filehelper.data.repository

import kotlinx.coroutines.flow.Flow
import nv.nam.filehelper.data.FileSource
import nv.nam.filehelper.data.local.LocalFileStorage
import nv.nam.filehelper.data.remote.RemoteFileStorage
import nv.nam.filehelper.domain.models.FileModel
import nv.nam.filehelper.domain.repository.FileRepository

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 25/7/2024
 * Github: https://github.com/Nam0101
 * @description :
 */
class FileRepositoryImpl(
    private val dataSource:FileSource
) : FileRepository {
    override suspend fun getAllFiles(page: Int, pageSize: Int): Flow<List<FileModel>> {
        return dataSource.getAllFiles(page, pageSize)
    }
}