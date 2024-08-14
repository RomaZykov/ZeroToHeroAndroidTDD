package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface ListLiveDataWrapper {

    interface Read : LiveDataWrapper.Read<List<CharSequence>>

    interface Save {
        fun save(bundleWrapper: BundleWrapper.Save)
    }

    interface Update : LiveDataWrapper.Update<List<CharSequence>>

    interface Add {
        fun add(source: CharSequence)
    }

    interface Mutable : Add, Update, Read, Save

    interface All : Add, Save, Update, Read, Mutable

    class Base : All, LiveDataWrapper.Abstract<List<CharSequence>>() {

        override fun add(source: CharSequence) {
            val currentList = liveData.value ?: ArrayList()
            val newList = ArrayList<CharSequence>(currentList)
            newList.add(source)
            liveData.value = newList

        }

        override fun save(bundleWrapper: BundleWrapper.Save) {
            val value = ArrayList<CharSequence>(liveData.value!!)
            bundleWrapper.save(value)
        }
    }
}