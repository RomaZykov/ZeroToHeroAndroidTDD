package ru.easycode.zerotoheroandroidtdd.data

import ru.easycode.zerotoheroandroidtdd.data.cache.LocalDataSource
import ru.easycode.zerotoheroandroidtdd.domain.Now

interface Repository {

    interface Read {
        fun list(): List<String>
    }

    interface Add {
        fun add(value: String)
    }

    interface Mutable : Add, Read

    class Base(private val dataSource: ItemsDao, private val now: Now) : Mutable {
        private val mapper = ItemMapper()

        override fun add(value: String) {
            dataSource.add(mapper.mapItemToItemDb(Item(now.nowMillis(), value)))
        }

        override fun list(): List<String> {
            return dataSource.list().map {
                mapper.mapItemDbToItem(it)
            }.map {
                it.text
            }
        }
    }
}
