package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.MutableLiveData

interface Navigation {

    interface Read : LiveDataWrapper.Read<Screen>

    interface Update : LiveDataWrapper.Update<Screen>

    interface Mutable : Read, Update

    class Base(
        private val liveDataWrapper: MutableLiveData<Screen> = SingleLiveEvent(),
    ) : Mutable, LiveDataWrapper.Abstract<Screen>()
}
