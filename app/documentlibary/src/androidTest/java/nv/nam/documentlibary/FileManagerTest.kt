package nv.nam.documentlibary

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import nv.nam.documentlibary.data.db.FileDao
import nv.nam.documentlibary.data.db.FileDatabase
import nv.nam.documentlibary.data.db.toFileModel
import nv.nam.documentlibary.domain.models.FileModel
import nv.nam.documentlibary.domain.models.toFileEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FileManagerTest {

    private lateinit var fileManager: FileManager
    private lateinit var fileDao: FileDao
    private lateinit var db: FileDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = FileDatabase.getDatabase(context)
        fileDao = db.fileDao()
        // Initialize FileManager with a mock implementation
        fileManager =
            FileManager.Builder().useLocalFileStorage().useContext(context).setNotPageSize().build()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun getFavoriteFiles_returnsCorrectFiles() = runTest {
        val favoriteFile1 = FileModel(
            name = "file1.txt",
            path = "/path/to/file1.txt",
            isDirectory = false,
            size = 1024,
            lastModifiedDate = "2023-01-01"
        ).toFileEntity().copy(isFavorite = true)
        val favoriteFile2 = FileModel(
            name = "file2.pdf",
            path = "/path/to/file2.pdf",
            isDirectory = false,
            size = 2048,
            lastModifiedDate = "2023-02-01"
        ).toFileEntity().copy(isFavorite = true)
        val nonFavoriteFile = FileModel(
            name = "file3.jpg",
            path = "/path/to/file3.jpg",
            isDirectory = false,
            size = 512,
            lastModifiedDate = "2023-03-01"
        ).toFileEntity()

        CoroutineScope(Dispatchers.IO).launch {
            fileDao.insertFile(favoriteFile1)
            fileDao.insertFile(favoriteFile2)
            fileDao.insertFile(nonFavoriteFile)
            val favoriteFiles = fileManager.getFavoriteFiles()

            assertThat(favoriteFiles).hasSize(2)
            assertThat(favoriteFiles).contains(favoriteFile1.toFileModel())
            assertThat(favoriteFiles).contains(favoriteFile2.toFileModel())
            assertThat(favoriteFiles).doesNotContain(nonFavoriteFile.toFileModel())
        }

    }

    @Test
    fun getAllFiles_returnsCorrectFiles() = runTest {
        val file1 = FileModel(
            name = "file1.txt",
            path = "/path/to/file1.txt",
            isDirectory = false,
            isFavorite = true,
            size = 1024,
            lastModifiedDate = "2023-01-01"
        ).toFileEntity()
        val file2 = FileModel(
            name = "file2.pdf",
            path = "/path/to/file2.pdf",
            isDirectory = false,
            isFavorite = true,
            size = 2048,
            lastModifiedDate = "2023-02-01"
        ).toFileEntity()
        val file3 = FileModel(
            name = "file3.jpg",
            path = "/path/to/file3.jpg",
            isDirectory = false,
            isFavorite = false,
            size = 512,
            lastModifiedDate = "2023-03-01"
        ).toFileEntity()

        CoroutineScope(Dispatchers.IO).launch {
            fileDao.insertFile(file1)
            fileDao.insertFile(file2)
            fileDao.insertFile(file3)
            val files = fileManager.getFavoriteFiles()

            assertThat(files).hasSize(2)
            assertThat(files).contains(file1.toFileModel())
            assertThat(files).contains(file2.toFileModel())
        }
    }
    @Test
    fun getAllPdfFiles_returnsCorrectFiles() = runTest {
        val pdfFile1 = FileModel(
            name = "file1.pdf",
            path = "/path/to/file1.pdf",
            isDirectory = false,
            isFavorite = true,
            size = 1024,
            lastModifiedDate = "2023-01-01"
        ).toFileEntity()
        val pdfFile2 = FileModel(
            name = "file2.pdf",
            path = "/path/to/file2.pdf",
            isDirectory = false,
            isFavorite = true,
            size = 2048,
            lastModifiedDate = "2023-02-01"
        ).toFileEntity()
        val nonPdfFile = FileModel(
            name = "file3.jpg",
            path = "/path/to/file3.jpg",
            isDirectory = false,
            isFavorite = false,
            size = 512,
            lastModifiedDate = "2023-03-01"
        ).toFileEntity()

        CoroutineScope(Dispatchers.IO).launch {
            fileDao.insertFile(pdfFile1)
            fileDao.insertFile(pdfFile2)
            fileDao.insertFile(nonPdfFile)
            val pdfFiles = fileManager.getAllPdfFiles()

            assertThat(pdfFiles).hasSize(2)
            assertThat(pdfFiles).contains(pdfFile1.toFileModel())
            assertThat(pdfFiles).contains(pdfFile2.toFileModel())
            assertThat(pdfFiles).doesNotContain(nonPdfFile.toFileModel())
        }
    }

    @Test
    fun setAndGetRecentFiles_returnsCorrectFiles() = runTest {
        val recentFile1 = FileModel(
            name = "file1.txt",
            path = "/path/to/file1.txt",
            isDirectory = false,
            isFavorite = true,
            size = 1024,
            lastModifiedDate = "2023-01-01"
        )
        val recentFile2 = FileModel(
            name = "file2.pdf",
            path = "/path/to/file2.pdf",
            isDirectory = false,
            isFavorite = true,
            size = 2048,
            lastModifiedDate = "2023-02-01"
        )
        val recentFile3 = FileModel(
            name = "file3.jpg",
            path = "/path/to/file3.jpg",
            isDirectory = false,
            isFavorite = false,
            size = 512,
            lastModifiedDate = "2023-03-01"
        )
        val entityFile1 = recentFile1.toFileEntity()
        val entityFile2 = recentFile2.toFileEntity()
        val entityFile3 = recentFile3.toFileEntity()

        CoroutineScope(Dispatchers.IO).launch {
            fileDao.insertFile(entityFile1)
            fileDao.insertFile(entityFile2)
            fileDao.insertFile(entityFile3)
            fileManager.updateLastAccessedDate(recentFile1)
            fileManager.updateLastAccessedDate(recentFile2)
            val recentFiles = fileManager.getRecentFiles(10)

            assertThat(recentFiles).hasSize(3)
            assertThat(recentFiles).contains(recentFile1)
            assertThat(recentFiles).contains(recentFile2)
            assertThat(recentFiles).contains(recentFile3)
        }
    }

}