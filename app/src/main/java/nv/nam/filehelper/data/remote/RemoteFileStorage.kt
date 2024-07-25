package nv.nam.filehelper.data.remote

import kotlinx.coroutines.flow.Flow
import nv.nam.filehelper.data.FileSource
import nv.nam.filehelper.domain.models.FileModel

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 25/7/2024
 * Github: https://github.com/Nam0101
 * @description : This class is used to handle remote file storage operations but it is not implemented yet
 */
class RemoteFileStorage : FileSource {
    // TODO: THIS CLASS IS NOT IMPLEMENTED YET
    override suspend fun getAllFiles(page: Int, pageSize: Int): Flow<List<FileModel>> {
        TODO("Not yet implemented")
    }
}