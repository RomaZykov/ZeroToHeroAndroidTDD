package ru.easycode.zerotoheroandroidtdd.core

import ru.easycode.zerotoheroandroidtdd.presentation.ItemUi

interface ListLiveDataWrapper {

    interface Add {
        fun add(value: ItemUi)
    }

    interface Read : LiveDataWrapper.Read<List<ItemUi>>

    interface Update : LiveDataWrapper.Update<List<ItemUi>>

    interface Delete {
        fun delete(item: ItemUi)
    }

    interface Mutable : Add, Read, Update, Delete

    interface All : Mutable

    class Base : All, LiveDataWrapper.Abstract<List<ItemUi>>() {

        override fun add(value: ItemUi) {
            val currentList = liveData.value ?: emptyList<ItemUi>()
            val newList = currentList + listOf(value)
            liveData.value = newList
        }

        override fun delete(item: ItemUi) {
            val currentList = liveData.value
            val newList: MutableList<ItemUi> = currentList?.toMutableList() ?: mutableListOf()
            newList.remove(item)
            liveData.value = newList
        }
    }
}