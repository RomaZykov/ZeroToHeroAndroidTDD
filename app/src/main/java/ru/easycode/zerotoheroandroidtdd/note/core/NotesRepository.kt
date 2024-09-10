package ru.easycode.zerotoheroandroidtdd.note.core

import ru.easycode.zerotoheroandroidtdd.core.Now
import ru.easycode.zerotoheroandroidtdd.folder.data.FoldersDao
import ru.easycode.zerotoheroandroidtdd.folder.data.cache.FolderCache
import ru.easycode.zerotoheroandroidtdd.note.data.MyNote
import ru.easycode.zerotoheroandroidtdd.note.data.NotesDao
import ru.easycode.zerotoheroandroidtdd.note.data.cache.NoteCache

interface NotesRepository {

    interface Create {
        suspend fun createNote(folderId: Long, text: String): MyNote
    }

    interface Edit {
        suspend fun note(noteId: Long): MyNote

        suspend fun deleteNote(noteId: Long)

        suspend fun renameNote(noteId: Long, newName: String)
    }

    interface ReadList {
        suspend fun noteList(folderId: Long): List<MyNote>
    }

    class Base(
        private val notesDao: NotesDao,
        private val foldersDao: FoldersDao,
        private val now: Now
    ) : NotesRepository, ReadList,
        Create, Edit {

        override suspend fun noteList(folderId: Long): List<MyNote> {
            val notesCache = notesDao.notes(folderId)
            return notesCache.map {
                it.to()
            }.sortedBy {
                it.id
            }
        }

        override suspend fun createNote(folderId: Long, text: String): MyNote {
            val id = now.timeInMillis()
            notesDao.insert(NoteCache(id, text, folderId))
            val folder = foldersDao.folders().find { it.id == folderId }
            if (folder != null) {
                val notesCount = notesDao.notes(folderId).size
                foldersDao.insert(FolderCache(folderId, folder.text, notesCount))
            }
            return notesDao.note(id).to()
        }

        override suspend fun note(noteId: Long): MyNote {
            return notesDao.note(noteId).to()
        }

        override suspend fun deleteNote(noteId: Long) {
            notesDao.delete(noteId)
        }

        override suspend fun renameNote(noteId: Long, newName: String) {
            val noteToRename = notesDao.note(noteId)
            val updatedNote = noteToRename.copy(text = newName)
            notesDao.insert(updatedNote)
        }
    }
}
