package nv.nam.documentlibary.domain.repository

import kotlinx.coroutines.flow.Flow
import nv.nam.documentlibary.domain.models.FileModel
import nv.nam.documentlibary.domain.models.FileType

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 25/7/2024
 * Github: https://github.com/Nam0101
 * @description : This interface is used to handle file repository use LocalFileStorage and RemoteFileStorage and it will be implemented in FileRepositoryImpl
 * at domain layer in clean architecture
 */
interface FileRepository {
    suspend fun getAllFiles(page: Int, pageSize: Int, fileType: FileType): Flow<List<FileModel>>

    suspend fun searchFileByName(fileName: String): Flow<List<FileModel>>

}