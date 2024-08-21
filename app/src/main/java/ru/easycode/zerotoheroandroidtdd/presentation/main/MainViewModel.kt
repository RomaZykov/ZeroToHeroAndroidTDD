package ru.easycode.zerotoheroandroidtdd.presentation.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import ru.easycode.zerotoheroandroidtdd.core.ListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.data.Repository

class MainViewModel(
    private val repository: Repository.Read,
    private val liveDataWrapper: ListLiveDataWrapper.Mutable,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) : ViewModel() {

    fun liveData() = liveDataWrapper.liveData()

    fun init() {
        CoroutineScope(dispatcherMain).launch {
            val result = mutableListOf<String>()
            CoroutineScope(dispatcher).launch {
                result.addAll(repository.list())
            }.join()

            liveDataWrapper.update(result)
        }
    }
}
