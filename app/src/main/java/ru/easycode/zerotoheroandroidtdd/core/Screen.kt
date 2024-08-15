package ru.easycode.zerotoheroandroidtdd.core

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.easycode.zerotoheroandroidtdd.list.ListFragment

interface Screen {

    fun navigate(
        supportFragmentManager: FragmentManager,
        containerId: Int,
    )

    abstract class Replace(private val fragment: Fragment) : Screen {
        override fun navigate(supportFragmentManager: FragmentManager, containerId: Int) {
            supportFragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commit()
        }
    }

    object Pop : Replace(ListFragment())
}

