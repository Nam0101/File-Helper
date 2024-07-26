package nv.nam.documentlibary.domain.models

/**
 * Enum class representing different file types and their associated extensions.
 *
 * @property extensions An array of file extensions associated with the file type.
 *
 * @constructor Creates a FileType with the specified extensions.
 */
enum class FileType(val extensions: Array<String>) {
    /**
     * Represents all file types.
     */
    ALL(emptyArray()),

    /**
     * Represents document file types.
     * Includes extensions: pdf, doc, docx, xls, xlsx, txt, ppt, pptx.
     */
    DOCUMENT(arrayOf("pdf", "doc", "docx", "xls", "xlsx", "txt", "ppt", "pptx")),

    /**
     * Represents PDF file types.
     * Includes extension: pdf.
     */
    PDF(arrayOf("pdf")),

    /**
     * Represents Word document file types.
     * Includes extensions: doc, docx.
     */
    WORD(arrayOf("doc", "docx")),

    /**
     * Represents Excel file types.
     * Includes extensions: xls, xlsx.
     */
    EXCEL(arrayOf("xls", "xlsx")),

    /**
     * Represents PowerPoint file types.
     * Includes extensions: ppt, pptx.
     */
    PPT(arrayOf("ppt", "pptx")),

    /**
     * Represents image file types.
     * Includes extensions: jpg, jpeg, png, gif, bmp.
     */
    IMAGE(arrayOf("jpg", "jpeg", "png", "gif", "bmp")),

    /**
     * Represents audio file types.
     * Includes extensions: mp3, wav, ogg, m4a.
     */
    AUDIO(arrayOf("mp3", "wav", "ogg", "m4a")),

    /**
     * Represents video file types.
     * Includes extensions: mp4, 3gp, mkv, avi, flv.
     */
    VIDEO(arrayOf("mp4", "3gp", "mkv", "avi", "flv")),

    /**
     * Represents APK file types.
     * Includes extension: apk.
     */
    APK(arrayOf("apk")),

    /**
     * Represents compressed file types.
     * Includes extensions: zip, rar.
     */
    ZIP(arrayOf("zip", "rar")),

    /**
     * Represents other file types not covered by the above categories.
     */
    OTHER(emptyArray());
}