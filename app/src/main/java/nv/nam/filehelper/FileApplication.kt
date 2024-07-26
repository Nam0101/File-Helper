package nv.nam.filehelper

import android.app.Application
import nv.nam.documentlibary.FileManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

/**
 * @author Nam Nguyen Van
 * Project: File Helper
 * Created: 25/7/2024
 * Github: https://github.com/Nam0101
 * @description : File Application
 */
class FileApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FileApplication)
            modules(appModule)
        }
    }
}

val appModule = module {
    viewModel<MainViewModel> { MainViewModel(get()) }

    single<FileManager> {
        FileManager.Builder().useLocalFileStorage().useContext(
            context = get(),
        ).setNotPageSize().build()
    }

}