package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.easycode.zerotoheroandroidtdd.create.CreateFragment
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

    object Pop : Screen.Replace(ListFragment())
}

