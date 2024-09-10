package ru.easycode.zerotoheroandroidtdd.folder.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.core.ClearViewModels
import ru.easycode.zerotoheroandroidtdd.core.Navigation
import ru.easycode.zerotoheroandroidtdd.core.Screen
import ru.easycode.zerotoheroandroidtdd.folder.FolderUi
import ru.easycode.zerotoheroandroidtdd.folder.core.FolderListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.core.FoldersRepository

class CreateFolderViewModel(
    private val repository: FoldersRepository.Create,
    private val navigation: Navigation.Update,
    private val clearViewModels: ClearViewModels,
    private val liveDataWrapper: FolderListLiveDataWrapper.Create,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    fun createFolder(name: String) {
        viewModelScope.launch(dispatcher) {
            val folderId = repository.createFolder(name)
            withContext(dispatcherMain) {
                liveDataWrapper.create(FolderUi(folderId, name, 0))
                comeback()
            }
        }
    }

    fun comeback() {
        clearViewModels.clear(this::class.java)
        navigation.update(Screen.Pop)
    }
}