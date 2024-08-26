package ru.easycode.zerotoheroandroidtdd.core

import ru.easycode.zerotoheroandroidtdd.presentation.ItemUi

interface ListLiveDataWrapper {

    interface Add {
        fun add(value: ItemUi)
    }

    interface Read : LiveDataWrapper.Read<List<ItemUi>>

    interface Update : LiveDataWrapper.Update<List<ItemUi>> {
        fun update(item: ItemUi)
    }

    interface Delete {
        fun delete(item: ItemUi)
    }

    interface Mutable : Add, Read, Update, Delete

    interface All : Mutable

    class Base : All, LiveDataWrapper.Abstract<List<ItemUi>>() {

        override fun update(item: ItemUi) {
            val currentList = liveData.value
            val found = liveData.value?.find { it.areItemsSame(item) }
            val newList: MutableList<ItemUi> = currentList?.toMutableList() ?: mutableListOf()
            newList[newList.indexOf(found)] = item
            liveData.value = newList
        }

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