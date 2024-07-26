package nv.nam.documentlibary.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 26/7/2024
 * Github: https://github.com/Nam0101
 * @description : This class represents the Room database for storing file information.
 */

@Database(entities = [FileEntity::class], version = 1)
abstract class FileDatabase : RoomDatabase() {

    abstract fun fileDao(): FileDao

    companion object {
        @Volatile
        private var INSTANCE: FileDatabase? = null

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