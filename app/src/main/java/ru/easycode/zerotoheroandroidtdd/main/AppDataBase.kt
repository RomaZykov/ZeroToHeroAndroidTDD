package ru.easycode.zerotoheroandroidtdd.main

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.easycode.zerotoheroandroidtdd.folder.data.FoldersDao
import ru.easycode.zerotoheroandroidtdd.folder.data.cache.FolderCache
import ru.easycode.zerotoheroandroidtdd.note.data.NotesDao
import ru.easycode.zerotoheroandroidtdd.note.data.cache.NoteCache


@Database(entities = [NoteCache::class, FolderCache::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun notesDao(): NotesDao
    abstract fun foldersDao(): FoldersDao

    companion object {

        private var INSTANCE: AppDataBase? = null
        private val LOCK = Any()

        fun getInstance(application: Application): AppDataBase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    AppDataBase::class.java,
                    "app_table"
                ).build()
                INSTANCE = db
                return db
            }
        }
    }
}
