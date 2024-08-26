package ru.easycode.zerotoheroandroidtdd.data

import ru.easycode.zerotoheroandroidtdd.core.Now

interface Repository {

    interface Read {
        fun list(): List<Item>
    }

    interface Add {
        fun add(value: String): Long
    }

    interface Update {
        fun update(id: Long, newText: String)
    }

    interface Delete {
        fun delete(id: Long)
        fun item(id: Long): Item
    }

    interface Change : Update, Delete

    interface Mutable : Add, Read, Change

    interface All : Mutable

    class Base(private val dataSource: ItemsDao, private val now: Now) : All {
        private val mapper = ItemMapper()

        override fun item(id: Long): Item {
            val itemCache = dataSource.item(id)
            val item = mapper.mapItemDbToItem(itemCache)
            return item
        }

        override fun update(id: Long, newText: String) {
            val itemCache = dataSource.item(id).copy(text = newText)
            dataSource.add(itemCache)
        }

        override fun delete(id: Long) {
            dataSource.delete(id)
        }

        override fun add(value: String): Long {
            val id = now.nowMillis()
            dataSource.add(mapper.mapItemToItemDb(Item(id, value)))
            return id
        }

        override fun list(): List<Item> {
            return dataSource.list().map {
                mapper.mapItemDbToItem(it)
            }
        }
    }
}