package ru.easycode.zerotoheroandroidtdd.note.core

import ru.easycode.zerotoheroandroidtdd.core.LiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.note.NoteUi

interface NoteLiveDataWrapper {

    interface Update : LiveDataWrapper.Update<NoteUi>
    interface Read : LiveDataWrapper.Read<NoteUi>

    fun update(noteText: String)

    class Base : NoteLiveDataWrapper, LiveDataWrapper.Abstract<NoteUi>() {

        override fun update(noteText: String) {
            val currentValue = livedata.value
            if (currentValue != null) {
                update(NoteUi(currentValue.id, noteText, currentValue.folderId))
            }
        }
    }
}
