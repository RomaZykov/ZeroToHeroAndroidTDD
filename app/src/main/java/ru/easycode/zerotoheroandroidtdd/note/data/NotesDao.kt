package ru.easycode.zerotoheroandroidtdd.note.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.easycode.zerotoheroandroidtdd.note.data.cache.NoteCache

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteCache)

    @Query("SELECT * FROM notes_table WHERE note_id = :noteId")
    suspend fun note(noteId: Long): NoteCache

    @Query("SELECT * FROM notes_table WHERE folder_id = :folderId")
    suspend fun notes(folderId: Long): List<NoteCache>

    @Query("DELETE FROM notes_table WHERE note_id = :noteId")
    suspend fun delete(noteId: Long)
}
