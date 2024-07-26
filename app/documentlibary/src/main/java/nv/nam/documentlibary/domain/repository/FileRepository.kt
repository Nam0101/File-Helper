package nv.nam.documentlibary.domain.repository

import kotlinx.coroutines.flow.Flow
import nv.nam.documentlibary.domain.models.FileModel
import nv.nam.documentlibary.domain.models.FileType

/**
 * Interface representing the file repository.
 * This interface is used to handle file repository using LocalFileStorage and RemoteFileStorage.
 * It will be implemented in FileRepositoryImpl at the domain layer in clean architecture.
 *
 * @author Nam Nguyen
 * Project: File Helper
 * Created: 25/7/2024
 * Github: https://github.com/Nam0101
 */
interface FileRepository {

    /**
     * Fetches all files from the repository based on the provided page, page size, and file type.
     *
     * @param page The page number to fetch.
     * @param pageSize The number of files per page.
     * @param fileType The type of files to fetch.
     * @return A Flow emitting a list of FileModel objects.
     */
    suspend fun getAllFiles(page: Int, pageSize: Int, fileType: FileType): Flow<List<FileModel>>

    /**
     * Searches for files by name in the repository.
     *
     * @param fileName The name of the file to search for.
     * @return A Flow emitting a list of FileModel objects that match the search criteria.
     */
    suspend fun searchFileByName(fileName: String): Flow<List<FileModel>>
}