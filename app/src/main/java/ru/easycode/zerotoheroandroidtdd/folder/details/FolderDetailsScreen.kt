package ru.easycode.zerotoheroandroidtdd.folder.details

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.core.Screen

data class FolderDetailsScreen(val id: Long) : Screen {
    override fun show(fragmentManager: FragmentManager) {
        val bundle = Bundle().apply {
            putLong("folder_id", id)
        }
        fragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(
                R.id.fragment_container_view,
                FolderDetailsFragment::class.java,
                bundle,
                "folder_details"
            )
            .addToBackStack("folders_list")
            .commit()
    }
}