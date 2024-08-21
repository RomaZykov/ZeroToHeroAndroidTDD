package ru.easycode.zerotoheroandroidtdd.data.cache

import androidx.room.Insert
import ru.easycode.zerotoheroandroidtdd.data.Item
import ru.easycode.zerotoheroandroidtdd.data.ItemMapper
import ru.easycode.zerotoheroandroidtdd.data.ItemsDao

interface LocalDataSource : ItemsDao {

    override fun list(): List<ItemCache>

    override fun add(item: ItemCache)

    class Base(
        private val dao: ItemsDao,
        private val itemToCache: ItemMapper = ItemMapper()
    ) : LocalDataSource {
        override fun list(): List<ItemCache> {
            return dao.list()
        }

        override fun add(item: ItemCache) {
            dao.add(item)
        }
    }
}
