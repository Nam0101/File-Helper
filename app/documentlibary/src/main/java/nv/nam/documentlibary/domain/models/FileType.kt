package nv.nam.documentlibary.domain.models

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 25/7/2024
 * Github: https://github.com/Nam0101
 * @description :
 */

enum class FileType(val extensions: Array<String>) {
    ALL(emptyArray()), DOCUMENT(
        arrayOf(
            "pdf", "doc", "docx", "xls", "xlsx", "txt", "ppt", "pptx"
        )
    ),
    PDF(arrayOf("pdf")), WORD(arrayOf("doc", "docx")), EXCEL(arrayOf("xls", "xlsx")), PPT(
        arrayOf(
            "ppt", "pptx"
        )
    ),
    IMAGE(arrayOf("jpg", "jpeg", "png", "gif", "bmp")), AUDIO(
        arrayOf(
            "mp3", "wav", "ogg", "m4a"
        )
    ),
    VIDEO(arrayOf("mp4", "3gp", "mkv", "avi", "flv")), APK(arrayOf("apk")), ZIP(
        arrayOf(
            "zip", "rar"
        )
    ),
    OTHER(emptyArray());
}