package ru.easycode.zerotoheroandroidtdd.data.cache

import ru.easycode.zerotoheroandroidtdd.data.ItemMapper
import ru.easycode.zerotoheroandroidtdd.data.ItemsDao

interface LocalDataSource : ItemsDao {

    override fun list(): List<ItemCache>

    override fun add(item: ItemCache)

    class Base(
        private val dao: ItemsDao,
        private val itemToCache: ItemMapper = ItemMapper()
    ) : LocalDataSource {
        override fun item(id: Long): ItemCache {
            return dao.item(id)
        }

        override fun delete(id: Long) {
            dao.delete(id)
        }

        override fun list(): List<ItemCache> {
            return dao.list()
        }

        override fun add(item: ItemCache) {
            dao.add(item)
        }
    }
}