package nv.nam.documentlibary.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data access object for managing file entities in the database.
 * Provides methods for inserting, updating, deleting, and querying file entities.
 *
 * @author Nam Nguyen
 * Project: File Helper
 * Created: 26/7/2024
 * Github: https://github.com/Nam0101
 */
@Dao
interface FileDao {

    /**
     * Inserts a file entity into the database.
     * If the file already exists, it replaces it.
     *
     * @param file The FileEntity object to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFile(file: FileEntity)

    /**
     * Updates an existing file entity in the database.
     *
     * @param file The FileEntity object to update.
     */
    @Update
    suspend fun updateFile(file: FileEntity)

    /**
     * Deletes a file entity from the database.
     *
     * @param file The FileEntity object to delete.
     */
    @Delete
    suspend fun deleteFile(file: FileEntity)

    /**
     * Retrieves a flow of favorite files from the database.
     *
     * @return A Flow emitting a list of FileEntity objects marked as favorite.
     */
    @Query("SELECT * FROM files WHERE is_favorite = 1")
    fun getFavoriteFiles(): Flow<List<FileEntity>>

    /**
     * Retrieves a flow of recent files from the database, limited by the specified number.
     *
     * @param limit The maximum number of recent files to retrieve.
     * @return A Flow emitting a list of FileEntity objects representing recent files.
     */
    @Query("SELECT * FROM files ORDER BY last_accessed_date DESC LIMIT :limit")
    fun getRecentFiles(limit: Int): Flow<List<FileEntity>>

    /**
     * Marks a file as favorite in the database by its path.
     *
     * @param path The path of the file to be marked as favorite.
     */
    @Query("UPDATE files SET is_favorite = 1 WHERE path = :path")
    fun addFileToFavorites(path: String)

    /**
     * Updates the last accessed date of a file in the database by its path.
     *
     * @param path The path of the file to update.
     */
    @Query("UPDATE files SET last_accessed_date = CURRENT_TIMESTAMP WHERE path = :path")
    fun updateLastAccessedDate(path: String)
}