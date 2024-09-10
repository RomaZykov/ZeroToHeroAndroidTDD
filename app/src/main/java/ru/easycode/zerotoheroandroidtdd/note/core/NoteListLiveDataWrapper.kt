package ru.easycode.zerotoheroandroidtdd.note.core

import ru.easycode.zerotoheroandroidtdd.core.LiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.note.NoteUi

interface NoteListLiveDataWrapper {

    interface Read : LiveDataWrapper.Read<List<NoteUi>>
    interface Update : LiveDataWrapper.Update<List<NoteUi>>

    interface Create {
        fun create(noteUi: NoteUi)
    }

    interface UpdateListAndRead : Read, Update {
        fun update(noteId: Long, newText: String)
    }

    interface Mutable : Read, Update

    interface All : Update, Create, UpdateListAndRead, Mutable

    class Base : All, LiveDataWrapper.Abstract<List<NoteUi>>() {
        override fun update(noteId: Long, newText: String) {
            val currentList = livedata.value
            val found = livedata.value?.find { it.id == noteId }
            val newList: MutableList<NoteUi> = currentList?.toMutableList() ?: mutableListOf()
            if (found != null) {
                newList[newList.indexOf(found)] = NoteUi(noteId, newText, found.folderId)
            }
            livedata.value = newList
        }

        override fun create(noteUi: NoteUi) {
            val currentList = livedata.value
            val newList = (currentList?.toMutableList() ?: mutableListOf()) + mutableListOf(noteUi)
            livedata.value = newList
        }
    }
}
