package nv.nam.documentlibary.data.remote

import kotlinx.coroutines.flow.Flow
import nv.nam.documentlibary.data.FileSource
import nv.nam.documentlibary.domain.models.FileModel
import nv.nam.documentlibary.domain.models.FileType

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 25/7/2024
 * Github: https://github.com/Nam0101
 * @description : This class is used to handle remote file storage operations but it is not implemented yet
 */
class RemoteFileStorage : FileSource {

    // TODO: THIS CLASS IS NOT IMPLEMENTED YET
    override suspend fun getAllFiles(
        page: Int, pageSize: Int, fileType: FileType
    ): Flow<List<FileModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchFileByName(fileName: String): Flow<List<FileModel>> {
        TODO("Not yet implemented")
    }
}