package nv.nam.documentlibary.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * This class represents the Room database for storing file information.
 * It provides an abstract method to get the DAO for file operations and a companion object
 * to get the singleton instance of the database.
 *
 * @property INSTANCE The singleton instance of the FileDatabase.
 *
 * @constructor Creates a Room database with the specified context.
 *
 * Project: File Helper
 * Created: 26/7/2024
 * Github: https://github.com/Nam0101
 * @description : This class represents the Room database for storing file information.
 */
@Database(entities = [FileEntity::class], version = 1, exportSchema = false)
abstract class FileDatabase : RoomDatabase() {

    /**
     * Abstract method to get the DAO for file operations.
     *
     * @return The FileDao instance for file operations.
     */
    abstract fun fileDao(): FileDao

    companion object {
        @Volatile
        private var INSTANCE: FileDatabase? = null

        /**
         * Gets the singleton instance of the FileDatabase.
         * If the instance is null, it creates a new database instance.
         *
         * @param context The application context.
         * @return The singleton instance of the FileDatabase.
         */
        fun getDatabase(context: Context): FileDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FileDatabase::class.java,
                    "file_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}