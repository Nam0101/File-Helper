package nv.nam.documentlibary.domain.usecase

import nv.nam.documentlibary.data.repository.FileDbRepositoryImpl
import nv.nam.documentlibary.domain.models.FileModel

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 26/7/2024
 * Github: https://github.com/Nam0101
 * @description :
 */
class GetDbFileUseCase(
    private val fileDbRepository: FileDbRepositoryImpl
) {
    suspend fun getFavoriteFiles() = fileDbRepository.getFavoriteFiles()

    suspend fun getRecentFiles(limit: Int) = fileDbRepository.getRecentFiles(limit)

    suspend fun addFileToFavorites(fileModel: FileModel) =
        fileDbRepository.addFileToFavorites(fileModel)

}