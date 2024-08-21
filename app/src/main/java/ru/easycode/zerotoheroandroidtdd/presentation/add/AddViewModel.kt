package ru.easycode.zerotoheroandroidtdd.presentation.add

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.easycode.zerotoheroandroidtdd.core.ClearViewModel
import ru.easycode.zerotoheroandroidtdd.core.ListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.data.Repository

class AddViewModel(
    private val repository: Repository.Add,
    private val liveDataWrapper: ListLiveDataWrapper.Add,
    private val clear: ClearViewModel,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) : ViewModel() {

    fun add(value: String) {
        CoroutineScope(dispatcher).launch {
            repository.add(value)
        }
        liveDataWrapper.add(value)
        comeback()
    }

    fun comeback() {
        clear.clearViewModel(this::class.java)
    }
}
