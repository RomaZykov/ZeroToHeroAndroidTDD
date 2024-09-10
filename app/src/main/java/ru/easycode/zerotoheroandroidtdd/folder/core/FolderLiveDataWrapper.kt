package ru.easycode.zerotoheroandroidtdd.folder.core

import ru.easycode.zerotoheroandroidtdd.core.LiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.FolderUi

interface FolderLiveDataWrapper {

    interface Update : LiveDataWrapper.Update<FolderUi>
    interface Read : LiveDataWrapper.Read<FolderUi>

    interface Rename {
        fun rename(newName: String)
    }

    interface Decrement {
        fun decrement()
    }

    interface Mutable : Update, Read

    class Base : Mutable, Rename, Decrement, LiveDataWrapper.Abstract<FolderUi>() {

        override fun decrement() {
            val currentValue = livedata.value
            val noteCounts = (currentValue?.notesCount ?: 0) - 1
            if (currentValue != null) {
                update(FolderUi(currentValue.id, currentValue.title, noteCounts))
            }
        }

        override fun rename(newName: String) {
            val currentValue = livedata.value
            if (currentValue != null) {
                update(FolderUi(currentValue.id, newName, currentValue.notesCount))
            }
        }
    }
}
