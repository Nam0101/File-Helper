package nv.nam.filehelper.data.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import nv.nam.filehelper.data.FileSource
import nv.nam.filehelper.domain.models.FileModel
import nv.nam.filehelper.domain.models.toFileModel
import java.io.File
import kotlin.coroutines.CoroutineContext

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 25/7/2024
 * Github: https://github.com/Nam0101
 * @description : This class is used to handle local file storage operations
 */
class LocalFileStorage : FileSource {
    private val localStorageDirectory = "/storage/emulated/0/"
    private val TAG = LocalFileStorage::class.java.simpleName

    private val contextDispatcher: CoroutineContext = Dispatchers.IO + Job()
    override suspend fun getAllFiles(page: Int, pageSize: Int): Flow<List<FileModel>> = flow {
        val allFiles = withContext(contextDispatcher) {
            getFilesFromInternalStorage()
        }
        val startIndex = (page - 1) * pageSize
        val endIndex =
            if (startIndex + pageSize < allFiles.size) startIndex + pageSize else allFiles.size

        emit(allFiles.subList(startIndex, endIndex))
    }

    private fun getFilesFromInternalStorage(): List<FileModel> {
        val directory = File(localStorageDirectory)
        val files = directory.listFiles() ?: return emptyList()
        return files.map {
            it.toFileModel()
        }
    }


}