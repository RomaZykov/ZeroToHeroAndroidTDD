package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface LiveDataWrapper {

    interface Read<T : Any> {
        fun liveData(): LiveData<T>
    }

    interface Update<T : Any> {
        fun update(list: T)
    }

    interface Mutable<T : Any> : Read<T>, Update<T>

    abstract class Abstract<T : Any>(protected val livedata: MutableLiveData<T> = SingleLiveEvent()) :
        Mutable<T> {
        override fun liveData(): LiveData<T> {
            return livedata
        }

        override fun update(list: T) {
            livedata.value = list
        }
    }
}