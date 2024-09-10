package ru.easycode.zerotoheroandroidtdd.note.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.easycode.zerotoheroandroidtdd.core.Mapper
import ru.easycode.zerotoheroandroidtdd.note.data.MyNote

@Entity(tableName = "notes_table")
data class NoteCache(
    @PrimaryKey @ColumnInfo(name = "note_id") val id: Long,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "folder_id") val folderId: Long
) : Mapper<MyNote> {
    override fun to(): MyNote {
        return MyNote(this.id, this.text, this.folderId)
    }
}
