package ru.easycode.zerotoheroandroidtdd.folder.create

import android.util.Log
import androidx.fragment.app.FragmentManager
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.core.Screen

object CreateFolderScreen : Screen {

    override fun show(fragmentManager: FragmentManager) {
        val currentFragment = fragmentManager.fragments.last()
        Log.d("Screen", "Fragments in container = ${fragmentManager.fragments}")
        fragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragment_container_view, CreateFolderFragment(), "create_folder")
            .addToBackStack("create_folder")
            .commit()
    }
}