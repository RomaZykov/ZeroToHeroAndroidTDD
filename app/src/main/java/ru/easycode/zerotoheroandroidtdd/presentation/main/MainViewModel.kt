package ru.easycode.zerotoheroandroidtdd.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.core.ListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.data.Repository
import ru.easycode.zerotoheroandroidtdd.presentation.ItemUi

class MainViewModel(
    private val repository: Repository.Read,
    private val liveDataWrapper: ListLiveDataWrapper.Mutable,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) : ViewModel() {

    fun liveData() = liveDataWrapper.liveData()

    fun init() {
        viewModelScope.launch(dispatcher) {
            val items = repository.list()
            withContext(dispatcherMain) {
                val listItemUi = items.map {
                    ItemUi(it.id, it.text)
                }
                liveDataWrapper.update(listItemUi)
            }
        }
    }
}