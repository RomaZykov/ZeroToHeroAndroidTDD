package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface LiveDataWrapper {
    interface Read<T : Any> {
        fun liveData(): LiveData<T>
    }

    interface Update<T : Any> {
        fun update(value: T)
    }

    interface Mutable<T : Any> : Update<T>, Read<T>

    abstract class Abstract<T : Any>(protected val liveData: MutableLiveData<T> = SingleLiveEvent()) :
        Mutable<T> {
        override fun liveData(): LiveData<T> {
            return liveData
        }

        override fun update(value: T) {
            liveData.postValue(value)
        }
    }
}