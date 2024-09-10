package ru.easycode.zerotoheroandroidtdd.folder.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.core.ClearViewModels
import ru.easycode.zerotoheroandroidtdd.core.Navigation
import ru.easycode.zerotoheroandroidtdd.core.Screen
import ru.easycode.zerotoheroandroidtdd.folder.core.FolderLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.edit.EditFolderScreen
import ru.easycode.zerotoheroandroidtdd.note.NoteUi
import ru.easycode.zerotoheroandroidtdd.note.core.NoteListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.note.core.NotesRepository
import ru.easycode.zerotoheroandroidtdd.note.create.CreateNoteScreen
import ru.easycode.zerotoheroandroidtdd.note.edit.EditNoteScreen

class FolderDetailsViewModel(
    private val noteListRepository: NotesRepository.ReadList,
    private val noteLiveDataWrapper: NoteListLiveDataWrapper.Mutable,
    private val folderLiveDataWrapper: FolderLiveDataWrapper.Mutable,
    private val navigation: Navigation.Update,
    private val clear: ClearViewModels,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    fun noteLiveData() = noteLiveDataWrapper.liveData()

    fun fetchFolderTitle() = folderLiveDataWrapper.liveData().value?.title

    fun init(folderId: Long) {
        viewModelScope.launch(dispatcher) {
            val notes = mutableListOf<NoteUi>()
            noteListRepository.noteList(folderId).forEach {
                notes.add(NoteUi(it.id, it.title, it.folderId))
            }
            withContext(dispatcherMain) {
                noteLiveDataWrapper.update(notes)
            }
        }
    }

    fun createNote(folderId: Long) {
        navigation.update(CreateNoteScreen(folderId))
    }

    fun editNote(folderId: Long, noteId: Long, noteTitle: String) {
        navigation.update(EditNoteScreen(folderId, noteId, noteTitle))
    }

    fun editFolder(folderId: Long, folderTitle: String) {
        navigation.update(EditFolderScreen(folderId, folderTitle))
    }

    fun comeback() {
        clear.clear(this::class.java)
        navigation.update(Screen.Pop)
    }
}