package ru.easycode.zerotoheroandroidtdd.core

import androidx.fragment.app.FragmentManager

interface Screen {

    fun show(fragmentManager: FragmentManager)

    object Pop : Screen {
        override fun show(fragmentManager: FragmentManager) {
            val currentFragment = fragmentManager.fragments.last()
            fragmentManager.popBackStack()
//            fragmentManager.beginTransaction()
//                .replace(R.id.fragment_container_view, currentFragment)
////                .remove(currentFragment)
//                .commit()
        }
    }
}
