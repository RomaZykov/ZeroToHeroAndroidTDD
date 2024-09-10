package ru.easycode.zerotoheroandroidtdd.note.edit

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.core.Screen

data class EditNoteScreen(val folderId: Long, val noteId: Long, val noteTitle: String) : Screen {
    override fun show(fragmentManager: FragmentManager) {
        val bundle = Bundle().apply {
            putLong("note_id", noteId)
            putString("note_title", noteTitle)
        }

        fragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(
                R.id.fragment_container_view,
                EditNoteFragment::class.java,
                bundle,
                "edit_note"
            )
            .addToBackStack("edit_note")
            .commit()
    }
}