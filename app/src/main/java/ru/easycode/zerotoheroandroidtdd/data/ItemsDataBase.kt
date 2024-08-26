package ru.easycode.zerotoheroandroidtdd.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.easycode.zerotoheroandroidtdd.data.cache.ItemCache

@Database(entities = [ItemCache::class], version = 1, exportSchema = false)
abstract class ItemsDataBase : RoomDatabase() {

    abstract fun itemsDao(): ItemsDao

    companion object {

        private var INSTANCE: ItemsDataBase? = null
        private val LOCK = Any()

        fun getInstance(application: Application): ItemsDataBase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    ItemsDataBase::class.java,
                    "items_table"
                ).build()
                INSTANCE = db
                return db
            }
        }
    }
}