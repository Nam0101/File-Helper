package nv.nam.documentlibary.domain.usecase

import nv.nam.documentlibary.data.repository.FileRepositoryImpl
import nv.nam.documentlibary.domain.models.FileType

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 25/7/2024
 * Github: https://github.com/Nam0101
 * @description :
 */
class GetFileUseCase(
    private val fileRepository: FileRepositoryImpl
) {
    suspend fun getAllFiles(page: Int, pageSize: Int, fileType: FileType = FileType.ALL) =
        fileRepository.getAllFiles(page, pageSize, fileType)

    suspend fun searchFileByName(fileName: String) = fileRepository.searchFileByName(fileName)
}