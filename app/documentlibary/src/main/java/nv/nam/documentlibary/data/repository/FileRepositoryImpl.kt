package nv.nam.documentlibary.data.repository

import kotlinx.coroutines.flow.Flow
import nv.nam.documentlibary.data.FileSource
import nv.nam.documentlibary.domain.models.FileModel
import nv.nam.documentlibary.domain.models.FileType
import nv.nam.documentlibary.domain.repository.FileRepository

/**
 * Implementation of the FileRepository interface.
 * This class provides methods to interact with the file data source.
 *
 * @property dataSource The data source for file operations.
 */
class FileRepositoryImpl(
    private val dataSource: FileSource
) : FileRepository {

    /**
     * Fetches all files from the data source based on the provided page, page size, and file type.
     *
     * @param page The page number to fetch.
     * @param pageSize The number of files per page.
     * @param fileType The type of files to fetch.
     * @return A Flow emitting a list of FileModel objects.
     */
    override suspend fun getAllFiles(page: Int, pageSize: Int, fileType: FileType): Flow<List<FileModel>> {
        return dataSource.getAllFiles(page, pageSize, fileType)
    }

    /**
     * Searches for files by name in the data source.
     *
     * @param fileName The name of the file to search for.
     * @return A Flow emitting a list of FileModel objects that match the search criteria.
     */
    override suspend fun searchFileByName(fileName: String): Flow<List<FileModel>> {
        return dataSource.searchFileByName(fileName)
    }
}