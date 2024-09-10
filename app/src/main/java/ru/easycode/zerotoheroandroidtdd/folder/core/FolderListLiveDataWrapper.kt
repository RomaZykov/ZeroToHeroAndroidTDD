package ru.easycode.zerotoheroandroidtdd.folder.core

import ru.easycode.zerotoheroandroidtdd.core.LiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.FolderUi

interface FolderListLiveDataWrapper {

    interface Update : LiveDataWrapper.Update<List<FolderUi>>
    interface Read : LiveDataWrapper.Read<List<FolderUi>>

    interface Create {
        fun create(folderUi: FolderUi)
    }

    interface UpdateListAndRead : Update, Read

    class Base : Create, UpdateListAndRead, LiveDataWrapper.Abstract<List<FolderUi>>() {
        override fun create(folderUi: FolderUi) {
            livedata.postValue(listOf(folderUi))
        }
    }
}
