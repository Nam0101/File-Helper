package nv.nam.filehelper.domain.models

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 25/7/2024
 * Github: https://github.com/Nam0101
 * @description : This class is used to store file information
 */
data class FileModel(
    val name: String,
    val path: String,
    val isDirectory: Boolean,
    val size: Long,
    val lastModifiedDate: String
)

fun FileModel.toFile(): File {
    return File(this.path)
}


fun File.toFileModel(): FileModel {
    return FileModel(
        name = this.name,
        path = this.absolutePath,
        isDirectory = this.isDirectory,
        size = this.length(),
        lastModifiedDate = formatDate(this.lastModified())
    )
}

fun formatDate(timeInMillis: Long): String {
    val date = Date(timeInMillis)
    val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return format.format(date)
}