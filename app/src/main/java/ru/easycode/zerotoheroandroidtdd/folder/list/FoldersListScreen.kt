package ru.easycode.zerotoheroandroidtdd.folder.list

import androidx.fragment.app.FragmentManager
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.core.Screen

object FoldersListScreen : Screen {

    override fun show(fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, FolderListFragment(), "folders_list")
            .commit()
    }
}