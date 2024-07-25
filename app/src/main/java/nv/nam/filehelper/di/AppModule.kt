package nv.nam.filehelper.di


import nv.nam.filehelper.data.FileSource
import nv.nam.filehelper.data.local.LocalFileStorage
import nv.nam.filehelper.data.remote.RemoteFileStorage
import nv.nam.filehelper.data.repository.FileRepositoryImpl
import nv.nam.filehelper.domain.repository.FileRepository
import org.koin.dsl.module


val appModule = module {
    single<FileSource> { LocalFileStorage() }
    single<FileRepository> { FileRepositoryImpl(get()) }
    single { RemoteFileStorage() }
    single { RemoteFileStorage() }

}