package nv.nam.documentlibary.data

import kotlinx.coroutines.flow.Flow
import nv.nam.documentlibary.domain.models.FileModel
import nv.nam.documentlibary.domain.models.FileType

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 25/7/2024
 * Github: https://github.com/Nam0101
 * @description :
 */
interface FileSource {
    suspend fun getAllFiles(page: Int, pageSize: Int, fileType: FileType): Flow<List<FileModel>>
    suspend fun searchFileByName(fileName: String): Flow<List<FileModel>>
}