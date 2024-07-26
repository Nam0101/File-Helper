package nv.nam.documentlibary.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import nv.nam.documentlibary.domain.models.FileModel
import nv.nam.documentlibary.domain.models.formatDate
import java.io.File

/**
 * Represents a file entity in the database.
 *
 * @property id The unique identifier of the file entity.
 * @property path The file path.
 * @property name The name of the file.
 * @property isDirectory Indicates if the file is a directory.
 * @property size The size of the file in bytes.
 * @property lastModifiedDate The last modified date of the file.
 * @property isFavorite Indicates if the file is marked as favorite.
 * @property lastAccessedDate The last accessed date of the file in milliseconds.
 */
@Entity(tableName = "files")
data class FileEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "path") val path: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "is_directory") val isDirectory: Boolean,
    @ColumnInfo(name = "size") val size: Long,
    @ColumnInfo(name = "last_modified_date") val lastModifiedDate: String,
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean = false,
    @ColumnInfo(name = "last_accessed_date") var lastAccessedDate: Long = 0
)

/**
 * Converts a FileEntity to a File object.
 *
 * @return The File object corresponding to the FileEntity.
 */
fun FileEntity.toFile(): File {
    return File(this.path)
}

/**
 * Converts a File object to a FileEntity.
 *
 * @return The FileEntity object corresponding to the File.
 */
fun File.toFileEntity(): FileEntity {
    return FileEntity(
        path = this.absolutePath,
        name = this.name,
        isDirectory = this.isDirectory,
        size = this.length(),
        lastModifiedDate = formatDate(this.lastModified()),
        lastAccessedDate = System.currentTimeMillis()
    )
}

/**
 * Converts a FileEntity to a FileModel.
 *
 * @return The FileModel object corresponding to the FileEntity.
 */
fun FileEntity.toFileModel(): FileModel {
    return FileModel(
        path = this.path,
        name = this.name,
        isDirectory = this.isDirectory,
        size = this.size,
        isFavorite = this.isFavorite,
        lastModifiedDate = this.lastModifiedDate,
    )
}

/**
 * Converts a list of FileEntity objects to a list of FileModel objects.
 *
 * @return The list of FileModel objects corresponding to the list of FileEntity objects.
 */
fun List<FileEntity>.toFileModel(): List<FileModel> {
    return this.map { it.toFileModel() }
}