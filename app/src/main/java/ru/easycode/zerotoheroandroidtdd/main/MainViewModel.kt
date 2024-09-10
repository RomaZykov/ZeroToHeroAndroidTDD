package ru.easycode.zerotoheroandroidtdd.main

import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.core.Navigation
import ru.easycode.zerotoheroandroidtdd.folder.list.FoldersListScreen

class MainViewModel(private val navigation: Navigation.Mutable) : ViewModel() {

    fun liveData() = navigation.liveData()

    fun init(firstRun: Boolean) {
        if (firstRun) {
            navigation.update(FoldersListScreen)
        }
    }
}
