package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.folder.core.FolderListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.core.FolderLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.core.FoldersRepository
import ru.easycode.zerotoheroandroidtdd.folder.create.CreateFolderViewModel
import ru.easycode.zerotoheroandroidtdd.folder.data.FoldersDao
import ru.easycode.zerotoheroandroidtdd.folder.details.FolderDetailsViewModel
import ru.easycode.zerotoheroandroidtdd.folder.edit.EditFolderViewModel
import ru.easycode.zerotoheroandroidtdd.folder.list.FolderListViewModel
import ru.easycode.zerotoheroandroidtdd.main.MainViewModel
import ru.easycode.zerotoheroandroidtdd.note.core.NoteListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.note.core.NoteLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.note.core.NotesRepository
import ru.easycode.zerotoheroandroidtdd.note.create.CreateNoteViewModel
import ru.easycode.zerotoheroandroidtdd.note.data.NotesDao
import ru.easycode.zerotoheroandroidtdd.note.edit.EditNoteViewModel

interface ProvideViewModel {

    fun <T : ViewModel> viewModel(clazz: Class<out ViewModel>): T

    class Base(
        private val foldersDao: FoldersDao,
        private val notesDao: NotesDao,
        private val now: Now,
        private val clear: ClearViewModels
    ) : ProvideViewModel {

        private val navigation = Navigation.Base()

        private val notesRepository = NotesRepository.Base(notesDao, foldersDao, now)
        private val noteLiveDataWrapper = NoteLiveDataWrapper.Base()
        private val notesListLiveDataWrapper = NoteListLiveDataWrapper.Base()

        private val foldersRepository = FoldersRepository.Base(now, foldersDao)
        private val folderLiveDataWrapper = FolderLiveDataWrapper.Base()
        private val foldersListLiveDataWrapper = FolderListLiveDataWrapper.Base()

        override fun <T : ViewModel> viewModel(clazz: Class<out ViewModel>): T {
            return when (clazz) {
                MainViewModel::class.java -> {
                    MainViewModel(navigation)
                }

                CreateFolderViewModel::class.java -> {
                    CreateFolderViewModel(
                        repository = foldersRepository,
                        navigation = navigation,
                        clearViewModels = clear,
                        liveDataWrapper = foldersListLiveDataWrapper
                    )
                }

                FolderDetailsViewModel::class.java -> {
                    FolderDetailsViewModel(
                        notesRepository,
                        noteLiveDataWrapper = notesListLiveDataWrapper,
                        folderLiveDataWrapper = folderLiveDataWrapper,
                        navigation = navigation,
                        clear = clear
                    )
                }

                EditFolderViewModel::class.java -> {
                    EditFolderViewModel(
                        folderLiveDataWrapper = folderLiveDataWrapper,
                        repository = foldersRepository,
                        navigation,
                        clear
                    )
                }

                FolderListViewModel::class.java -> {
                    FolderListViewModel(
                        foldersRepository,
                        foldersListLiveDataWrapper,
                        folderLiveDataWrapper,
                        navigation
                    )
                }

                CreateNoteViewModel::class.java -> {
                    CreateNoteViewModel(
                        repository = notesRepository,
                        navigation = navigation,
                        addLiveDataWrapper = notesListLiveDataWrapper,
                        clear = clear
                    )
                }

                EditNoteViewModel::class.java -> {
                    EditNoteViewModel(
                        folderLiveDataWrapper = folderLiveDataWrapper,
                        noteLiveDataWrapper = noteLiveDataWrapper,
                        notesListLiveDataWrapper,
                        notesRepository,
                        navigation,
                        clear
                    )
                }

                else -> throw Exception("Unknown viewModel class $clazz")
            } as T
        }
    }
}