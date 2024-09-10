package ru.easycode.zerotoheroandroidtdd.folder.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.core.Navigation
import ru.easycode.zerotoheroandroidtdd.folder.FolderUi
import ru.easycode.zerotoheroandroidtdd.folder.core.FolderListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.core.FolderLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.core.FoldersRepository
import ru.easycode.zerotoheroandroidtdd.folder.create.CreateFolderScreen
import ru.easycode.zerotoheroandroidtdd.folder.details.FolderDetailsScreen

class FolderListViewModel(
    private val repository: FoldersRepository.ReadList,
    private val listLiveDataWrapper: FolderListLiveDataWrapper.UpdateListAndRead,
    private val folderLiveDataWrapper: FolderLiveDataWrapper.Update,
    private val navigation: Navigation.Update,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    fun liveData() = listLiveDataWrapper.liveData()

    fun init() {
        viewModelScope.launch(dispatcher) {
            val foldersUi = repository.folders().map {
                FolderUi(it.id, it.title, it.notesCount)
            }
            withContext(dispatcherMain) {
                listLiveDataWrapper.update(foldersUi)
            }
        }
    }

    fun addFolder() {
        navigation.update(CreateFolderScreen)
    }

    fun folderDetails(folderUi: FolderUi) {
        viewModelScope.launch(dispatcherMain) {
            folderLiveDataWrapper.update(folderUi)
        }
        navigation.update(FolderDetailsScreen(folderUi.id))
    }

}