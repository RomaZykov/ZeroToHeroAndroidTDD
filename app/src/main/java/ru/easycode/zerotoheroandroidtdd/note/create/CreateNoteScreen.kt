package ru.easycode.zerotoheroandroidtdd.note.create

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.core.Screen

data class CreateNoteScreen(val folderId: Long) : Screen {
    override fun show(fragmentManager: FragmentManager) {
        val bundle = Bundle().apply {
            putLong("folder_id", folderId)
        }

        fragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragment_container_view, CreateNoteFragment::class.java, bundle, "note")
            .addToBackStack("folder_details")
            .commit()
    }
}