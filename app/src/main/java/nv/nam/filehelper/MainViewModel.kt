package nv.nam.filehelper

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nv.nam.documentlibary.FileManager
import nv.nam.documentlibary.domain.models.FileModel

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 26/7/2024
 * Github: https://github.com/Nam0101
 * @description :
 */
class MainViewModel(
    private val fileManager: FileManager
) : ViewModel() {

    private val _files = MutableLiveData<List<FileModel>>()
    val files = _files

    fun getAllFiles(page: Int, pageSize: Int) {
        viewModelScope.launch {
            val files = fileManager.getAllPdfFiles()
            _files.postValue(files)
        }
    }
}