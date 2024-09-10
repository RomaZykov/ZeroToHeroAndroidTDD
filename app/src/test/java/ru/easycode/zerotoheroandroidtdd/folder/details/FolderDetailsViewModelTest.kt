package ru.easycode.zerotoheroandroidtdd.folder.details

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.easycode.zerotoheroandroidtdd.core.FakeClear
import ru.easycode.zerotoheroandroidtdd.core.FakeClear.Companion.CLEAR
import ru.easycode.zerotoheroandroidtdd.core.FakeNavigation
import ru.easycode.zerotoheroandroidtdd.core.FakeNavigation.Companion.NAVIGATE
import ru.easycode.zerotoheroandroidtdd.core.Order
import ru.easycode.zerotoheroandroidtdd.core.Screen
import ru.easycode.zerotoheroandroidtdd.folder.edit.EditFolderScreen
import ru.easycode.zerotoheroandroidtdd.note.NoteUi
import ru.easycode.zerotoheroandroidtdd.note.core.NoteListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.note.core.NotesRepository
import ru.easycode.zerotoheroandroidtdd.note.create.CreateNoteScreen
import ru.easycode.zerotoheroandroidtdd.note.data.MyNote
import ru.easycode.zerotoheroandroidtdd.note.edit.EditNoteScreen

class FolderDetailsViewModelTest {

    private lateinit var viewModel: FolderDetailsViewModel
    private lateinit var clear: FakeClear
    private lateinit var noteListRepository: FakeNoteListRepository
    private lateinit var folderLiveDataWrapper: FakeFolderLiveDataWrapper
    private lateinit var noteListLiveDataWrapper: FakeNoteListLiveDataWrapper
    private lateinit var navigation: FakeNavigation
    private lateinit var order: Order

    @Before
    fun setup() {
        order = Order()
        clear = FakeClear.Base(order)
        noteListRepository = FakeNoteListRepository.Base(order)
        noteListLiveDataWrapper = FakeNoteListLiveDataWrapper.Base(order)
        folderLiveDataWrapper = FakeFolderLiveDataWrapper.Base(order)
        navigation = FakeNavigation.Base(order)
        viewModel = FolderDetailsViewModel(
            noteListRepository = noteListRepository,
            noteLiveDataWrapper = noteListLiveDataWrapper,
            folderLiveDataWrapper = folderLiveDataWrapper,
            navigation = navigation,
            clear = clear,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined
        )
    }

    @Test
    fun test_init() {
        noteListRepository.expectNotes(
            listOf(
                MyNote(id = 1L, title = "first note", folderId = 7L),
                MyNote(id = 2L, title = "second note", folderId = 7L)
            )
        )

        viewModel.init(folderId = 7L)
        noteListLiveDataWrapper.check(
            listOf(
                NoteUi(id = 1L, title = "first note", folderId = 7L),
                NoteUi(id = 2L, title = "second note", folderId = 7L)
            )
        )
        noteListRepository.checkFolderId(7L)

        order.check(listOf(READ_NOTES_REPOSITORY, UPDATE_NOTES_LIVEDATA))
    }

    @Test
    fun test_create_note() {
        viewModel.createNote(folderId = 9L)
        navigation.checkScreen(CreateNoteScreen(folderId = 9L))
        order.check(listOf(NAVIGATE))
    }

    @Test
    fun test_edit_note() {
        viewModel.editNote(folderId = 9L, 1L, "note 1")
        navigation.checkScreen(EditNoteScreen(folderId = 9L, 1L, "note 1"))
        order.check(listOf(NAVIGATE))
    }

    @Test
    fun test_edit_folder() {
        viewModel.editFolder(folderId = 9L, "first folder")
        navigation.checkScreen(EditFolderScreen(folderId = 9L, "first folder"))
        order.check(listOf(NAVIGATE))
    }

    @Test
    fun test_comeback() {
        viewModel.comeback()
        clear.check(listOf(FolderDetailsViewModel::class.java))
        navigation.checkScreen(Screen.Pop)
        order.check(listOf(CLEAR, NAVIGATE))
    }
}

private const val READ_NOTES_REPOSITORY = "NotesRepository.ReadList#noteList"
private const val UPDATE_NOTES_LIVEDATA = "NoteListLiveDataWrapper.UpdateListAndRead#update"

private interface FakeNoteListRepository : NotesRepository.ReadList {

    fun expectNotes(list: List<MyNote>)

    fun checkFolderId(expected: Long)

    class Base(private val order: Order) : FakeNoteListRepository {

        private var actualFolderId: Long = -1

        private val actual = mutableListOf<MyNote>()

        override fun expectNotes(list: List<MyNote>) {
            actual.clear()
            actual.addAll(list)
        }

        override suspend fun noteList(folderId: Long): List<MyNote> {
            actualFolderId = folderId
            order.add(READ_NOTES_REPOSITORY)
            return actual
        }

        override fun checkFolderId(expected: Long) {
            assertEquals(expected, actualFolderId)
        }
    }
}

private interface FakeNoteListLiveDataWrapper : NoteListLiveDataWrapper.Mutable {

    fun check(expected: List<NoteUi>)

    class Base(private val order: Order) : FakeNoteListLiveDataWrapper {

        private val actual = mutableListOf<NoteUi>()

        override fun update(notes: List<NoteUi>) {
            actual.clear()
            actual.addAll(notes)
            order.add(UPDATE_NOTES_LIVEDATA)
        }

        override fun liveData(): LiveData<List<NoteUi>> {
            throw IllegalStateException("Not used")
        }

        override fun check(expected: List<NoteUi>) {
            assertEquals(expected, actual)
        }
    }
}