package ru.easycode.zerotoheroandroidtdd.folder.core

import ru.easycode.zerotoheroandroidtdd.core.Now
import ru.easycode.zerotoheroandroidtdd.folder.data.Folder
import ru.easycode.zerotoheroandroidtdd.folder.data.FoldersDao
import ru.easycode.zerotoheroandroidtdd.folder.data.cache.FolderCache

interface FoldersRepository {

    interface Create {
        suspend fun createFolder(name: String): Long
    }

    interface ReadList {
        suspend fun folders(): List<Folder>
    }

    interface Edit {
        suspend fun delete(folderId: Long)
        suspend fun rename(folderId: Long, newName: String)
    }

    interface Mutable : Create, ReadList, Edit

    class Base(
        private val now: Now,
        private val foldersDao: FoldersDao
    ) : Mutable {

        override suspend fun createFolder(name: String): Long {
            val folderId = now.timeInMillis()
            foldersDao.insert(FolderCache(folderId, name, 0))
            return folderId
        }

        override suspend fun folders(): List<Folder> {
            val foldersCache = foldersDao.folders()
            val folders = mutableListOf<Folder>()
            foldersCache.map {
                folders.add(it.to())
            }
            return folders
        }

        override suspend fun delete(folderId: Long) {
            foldersDao.delete(folderId)
        }

        override suspend fun rename(folderId: Long, newName: String) {
            val folder = foldersDao.folders().find { it.id == folderId }
            foldersDao.folders().let {
                if (folder != null) {
                    foldersDao.insert(folder.copy(text = newName))
                }
            }
        }
    }
}
