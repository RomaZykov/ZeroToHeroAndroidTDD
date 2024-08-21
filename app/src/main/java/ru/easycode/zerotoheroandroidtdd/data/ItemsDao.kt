package ru.easycode.zerotoheroandroidtdd.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.easycode.zerotoheroandroidtdd.data.cache.ItemCache
import ru.easycode.zerotoheroandroidtdd.data.cache.LocalDataSource

@Dao
interface ItemsDao {

    @Query("SELECT * FROM items")
    fun list(): List<ItemCache>

    @Insert
    fun add(item: ItemCache)
}
