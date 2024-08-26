package ru.easycode.zerotoheroandroidtdd.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import ru.easycode.zerotoheroandroidtdd.core.ClearViewModel
import ru.easycode.zerotoheroandroidtdd.core.ListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.data.Repository
import ru.easycode.zerotoheroandroidtdd.presentation.ItemUi

class AddViewModel(
    private val repository: Repository.Add,
    private val liveDataWrapper: ListLiveDataWrapper.Add,
    private val clear: ClearViewModel,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) : ViewModel() {

    fun add(value: String) {
        viewModelScope.launch(dispatcherMain) {
            var id = 0L
            viewModelScope.launch(dispatcher) {
                id = repository.add(value)
            }.join()
            liveDataWrapper.add(ItemUi(id, value))
        }
        comeback()
    }

    fun comeback() {
        clear.clearViewModel(this::class.java)
    }
}