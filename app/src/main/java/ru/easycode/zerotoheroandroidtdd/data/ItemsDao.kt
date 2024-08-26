package ru.easycode.zerotoheroandroidtdd.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.easycode.zerotoheroandroidtdd.data.cache.ItemCache

@Dao
interface ItemsDao {

    @Query("SELECT * FROM items")
    fun list(): List<ItemCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(item: ItemCache)

    @Query("SELECT * FROM items WHERE item_id = :id")
    fun item(id: Long): ItemCache

    @Query("DELETE FROM items WHERE item_id = :id")
    fun delete(id: Long)
}