package nv.nam.filehelper.data.local

import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import nv.nam.filehelper.data.FileSource
import nv.nam.filehelper.domain.models.FileModel
import nv.nam.filehelper.domain.models.FileType
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
    private val localStorageDirectory = "/storage/emulated/0"
    private val TAG = LocalFileStorage::class.java.simpleName
    private var cachedFiles: List<FileModel>? = null
    private var updateJob: Job? = null
    private val contextDispatcher: CoroutineContext = Dispatchers.IO + Job()
    override suspend fun getAllFiles(
        page: Int, pageSize: Int, fileType: FileType
    ): Flow<List<FileModel>> = flow {
        cachedFiles?.let {
            emit(it.chunked(pageSize).getOrNull(page - 1) ?: emptyList())
        }

        if (updateJob?.isActive == true) {
            updateJob?.join()
        }

        updateJob = Job()
        withContext(contextDispatcher) {
            cachedFiles = getFilesFromInternalStorage(fileType = fileType)
        }
        (updateJob as CompletableJob).complete()
        emit(cachedFiles?.chunked(pageSize)?.getOrNull(page - 1) ?: emptyList())
    }


    private fun getFilesFromInternalStorage(
        directoryPath: String = localStorageDirectory, fileType: FileType = FileType.ALL
    ): List<FileModel> = sequence {
        val directory = File(directoryPath)
        if (directory.exists() && directory.isDirectory) {
            directory.listFiles()?.forEach { file ->
                if (file.isFile) {
                    if (fileType == FileType.ALL || file.extension.lowercase() in fileType.extensions) {
                        yield(file.toFileModel())
                    }
                } else if (file.isDirectory) {
                    yieldAll(getFilesFromInternalStorage(file.absolutePath, fileType))
                }
            }
        }
    }.toList()
}
