package nv.nam.documentlibary.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 26/7/2024
 * Github: https://github.com/Nam0101
 * @description : Data access object for managing file entities in the database.
 */

@Dao
interface FileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFile(file: FileEntity)

    @Update
    suspend fun updateFile(file: FileEntity)

    @Delete
    suspend fun deleteFile(file: FileEntity)

    @Query("SELECT * FROM files WHERE is_favorite = 1")
    fun getFavoriteFiles(): Flow<List<FileEntity>>

    @Query("SELECT * FROM files ORDER BY last_accessed_date DESC LIMIT :limit")
    fun getRecentFiles(limit: Int): Flow<List<FileEntity>>

    @Query("UPDATE files SET is_favorite = 1 WHERE  path = :path")
    fun addFileToFavorites(path: String)

    @Query("UPDATE files SET last_accessed_date = CURRENT_TIMESTAMP WHERE path = :path")
    fun updateLastAccessedDate(path: String)
}