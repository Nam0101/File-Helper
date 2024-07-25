package nv.nam.filehelper.domain.usecase

import nv.nam.filehelper.domain.repository.FileRepository

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 25/7/2024
 * Github: https://github.com/Nam0101
 * @description :
 */
class GetFileUseCase(
    private val fileRepository: FileRepository
) {
    suspend fun getAllFiles(page: Int, pageSize: Int) = fileRepository.getAllFiles(page, pageSize)
}