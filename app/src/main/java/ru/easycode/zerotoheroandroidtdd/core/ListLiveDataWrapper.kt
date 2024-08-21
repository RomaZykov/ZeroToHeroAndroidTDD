package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface ListLiveDataWrapper {

    interface Read : LiveDataWrapper.Read<List<String>>

//    interface Save {
//        fun save(bundleWrapper: BundleWrapper.Save)
//    }

    interface Update : LiveDataWrapper.Update<List<String>>

    interface Add {
        fun add(value: String)
    }

    interface Mutable : Read, Update

    interface All : Add, Mutable

    class Base : All, LiveDataWrapper.Abstract<List<String>>() {

        override fun add(value: String) {
            val currentList = liveData.value ?: emptyList()
            val newList = currentList + listOf(value)
            liveData.value = newList
        }
    }
}