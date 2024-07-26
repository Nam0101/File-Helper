package nv.nam.documentlibary.domain.usecase

import nv.nam.documentlibary.data.repository.FileRepositoryImpl
import nv.nam.documentlibary.domain.models.FileType

/**
 * Use case class for file operations.
 * Provides methods to interact with the file repository.
 *
 * @property fileRepository The repository implementation for file operations.
 *
 * @constructor Creates a GetFileUseCase with the specified repository.
 *
 * @param fileRepository The repository implementation for file operations.
 *
 * Project: File Helper
 * Created: 25/7/2024
 * Github: https://github.com/Nam0101
 * @description :
 */
class GetFileUseCase(
    private val fileRepository: FileRepositoryImpl
) {
    /**
     * Fetches all files from the repository based on the provided page, page size, and file type.
     *
     * @param page The page number to fetch.
     * @param pageSize The number of files per page.
     * @param fileType The type of files to fetch. Defaults to FileType.ALL.
     * @return A Flow emitting a list of FileModel objects.
     */
    suspend fun getAllFiles(page: Int, pageSize: Int, fileType: FileType = FileType.ALL) =
        fileRepository.getAllFiles(page, pageSize, fileType)

    /**
     * Searches for files by name in the repository.
     *
     * @param fileName The name of the file to search for.
     * @return A Flow emitting a list of FileModel objects that match the search criteria.
     */
    suspend fun searchFileByName(fileName: String) = fileRepository.searchFileByName(fileName)
}