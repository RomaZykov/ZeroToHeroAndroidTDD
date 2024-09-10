package ru.easycode.zerotoheroandroidtdd.folder.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.easycode.zerotoheroandroidtdd.folder.data.cache.FolderCache

@Dao
interface FoldersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(folder: FolderCache)

    @Query("SELECT * FROM folders_table")
    suspend fun folders(): List<FolderCache>

    @Query("DELETE FROM folders_table WHERE folder_id = :folderId")
    suspend fun delete(folderId: Long)
}
