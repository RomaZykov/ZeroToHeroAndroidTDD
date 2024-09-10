package ru.easycode.zerotoheroandroidtdd.folder.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.easycode.zerotoheroandroidtdd.core.Mapper
import ru.easycode.zerotoheroandroidtdd.folder.data.Folder


@Entity(tableName = "folders_table")
data class FolderCache(
    @PrimaryKey @ColumnInfo(name = "folder_id") val id: Long,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "notes_count") val notesCount: Int
) : Mapper<Folder> {
    override fun to(): Folder {
        return Folder(
            id = this.id,
            title = this.text,
            notesCount = this.notesCount
        )
    }
}
