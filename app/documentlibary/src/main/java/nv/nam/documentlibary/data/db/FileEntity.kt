package nv.nam.documentlibary.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import nv.nam.documentlibary.domain.models.FileModel
import nv.nam.documentlibary.domain.models.formatDate
import java.io.File

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 25/7/2024
 * Github: https://github.com/Nam0101
 * @description : This class represents a file entity in the database.
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

fun FileEntity.toFile(): File {
    return File(this.path)
}

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

fun List<FileEntity>.toFileModel(): List<FileModel> {
    return this.map { it.toFileModel() }
}