package ru.easycode.zerotoheroandroidtdd.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        viewModelScope.launch(dispatcher) {
            val value = repository.list()
            withContext(dispatcherMain) {
                liveDataWrapper.update(value)
            }
        }
    }
}
