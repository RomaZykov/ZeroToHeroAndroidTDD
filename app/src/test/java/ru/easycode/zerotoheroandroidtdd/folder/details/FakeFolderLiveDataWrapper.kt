package ru.easycode.zerotoheroandroidtdd.folder.details

import androidx.lifecycle.LiveData
import junit.framework.TestCase.assertEquals
import ru.easycode.zerotoheroandroidtdd.core.Order
import ru.easycode.zerotoheroandroidtdd.folder.FolderUi
import ru.easycode.zerotoheroandroidtdd.folder.core.FolderLiveDataWrapper

interface FakeFolderLiveDataWrapper : FolderLiveDataWrapper.Mutable {

    fun check(expected: FolderUi)

    class Base(private val order: Order) : FakeFolderLiveDataWrapper {

        private lateinit var actual: FolderUi

        override fun check(expected: FolderUi) {
            assertEquals(expected, actual)
        }

        override fun update(folder: FolderUi) {
            order.add(UPDATE_FOLDER_LIVEDATA)
            actual = folder
        }

        override fun liveData(): LiveData<FolderUi> {
            throw Exception("Not used")
        }
    }
}

const val UPDATE_FOLDER_LIVEDATA = "FolderLiveDataWrapper.Mutable#update"