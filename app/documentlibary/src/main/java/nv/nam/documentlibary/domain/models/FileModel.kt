package nv.nam.documentlibary.domain.models

import nv.nam.documentlibary.data.db.FileEntity
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Data class representing a file model.
 *
 * @property name The name of the file.
 * @property path The path of the file.
 * @property isDirectory Indicates if the file is a directory.
 * @property isFavorite Indicates if the file is marked as favorite.
 * @property size The size of the file in bytes.
 * @property lastModifiedDate The last modified date of the file.
 */
data class FileModel(
    val name: String,
    val path: String,
    val isDirectory: Boolean,
    val isFavorite: Boolean = false,
    val size: Long,
    val lastModifiedDate: String
)

/**
 * Converts a FileModel to a File object.
 *
 * @return The File object corresponding to the FileModel.
 */
fun FileModel.toFile(): File {
    return File(this.path)
}

/**
 * Converts a FileModel to a FileEntity.
 *
 * @return The FileEntity object corresponding to the FileModel.
 */
fun FileModel.toFileEntity(): FileEntity {
    return FileEntity(
        name = this.name,
        path = this.path,
        isDirectory = this.isDirectory,
        size = this.size,
        isFavorite = this.isFavorite,
        lastModifiedDate = this.lastModifiedDate
    )
}

/**
 * Converts a File object to a FileModel.
 *
 * @return The FileModel object corresponding to the File.
 */
fun File.toFileModel(): FileModel {
    return FileModel(
        name = this.name,
        path = this.absolutePath,
        isDirectory = this.isDirectory,
        size = this.length(),
        lastModifiedDate = formatDate(this.lastModified())
    )
}

/**
 * Formats a given time in milliseconds to a date string.
 *
 * @param timeInMillis The time in milliseconds to format.
 * @return The formatted date string.
 */
fun formatDate(timeInMillis: Long): String {
    val date = Date(timeInMillis)
    val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return format.format(date)
}