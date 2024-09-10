package ru.easycode.zerotoheroandroidtdd.folder.edit

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.core.Screen

data class EditFolderScreen(val folderId: Long, val folderTitle: String) : Screen {
    override fun show(fragmentManager: FragmentManager) {
        val bundle = Bundle().apply {
            putLong("folder_id", folderId)
            putString("folder_title", folderTitle)
        }

        fragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(
                R.id.fragment_container_view,
                EditFolderFragment::class.java,
                bundle,
                "edit_folder"
            )
            .addToBackStack("edit_folder")
            .commit()
    }
}