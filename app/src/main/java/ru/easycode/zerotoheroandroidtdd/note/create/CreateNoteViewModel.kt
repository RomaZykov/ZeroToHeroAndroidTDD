package ru.easycode.zerotoheroandroidtdd.note.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.core.ClearViewModels
import ru.easycode.zerotoheroandroidtdd.core.Navigation
import ru.easycode.zerotoheroandroidtdd.core.Screen
import ru.easycode.zerotoheroandroidtdd.note.NoteUi
import ru.easycode.zerotoheroandroidtdd.note.core.NoteListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.note.core.NotesRepository

class CreateNoteViewModel(
    private val addLiveDataWrapper: NoteListLiveDataWrapper.Create,
    private val navigation: Navigation.Update,
    private val repository: NotesRepository.Create,
    private val clear: ClearViewModels,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    fun createNote(folderId: Long, text: String) {
        viewModelScope.launch(dispatcher) {
            val note = repository.createNote(folderId, text)
            val noteUi = NoteUi(note.id, note.title, note.folderId)
            withContext(dispatcherMain) {
                addLiveDataWrapper.create(noteUi)
                comeback()
            }
        }
    }

    fun comeback() {
        clear.clear(this::class.java)
        navigation.update(Screen.Pop)
    }
}