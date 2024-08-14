package ru.easycode.zerotoheroandroidtdd.core

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.easycode.zerotoheroandroidtdd.MainActivity
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.Screen

interface Navigation {

    interface Read : LiveDataWrapper.Read<Screen>

    interface Update : LiveDataWrapper.Update<Screen>

    interface Mutable : Update, Read

    class Base(
        private val liveDataWrapper: MutableLiveData<Screen> = SingleLiveEvent(),
    ) : Mutable, LiveDataWrapper.Abstract<Screen>()
}