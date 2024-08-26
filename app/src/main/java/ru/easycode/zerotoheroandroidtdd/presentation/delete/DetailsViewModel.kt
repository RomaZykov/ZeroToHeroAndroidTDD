package ru.easycode.zerotoheroandroidtdd.presentation.delete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.core.ClearViewModel
import ru.easycode.zerotoheroandroidtdd.core.ListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.data.ItemMapper
import ru.easycode.zerotoheroandroidtdd.data.Repository

class DetailsViewModel(
    private val repository: Repository.Change,
    private val changeLiveDataWrapper: ListLiveDataWrapper.Mutable,
    private val clear: ClearViewModel,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) : ViewModel() {

    private var _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> = _liveData

    fun init(itemId: Long) {
        viewModelScope.launch(dispatcher) {
            val item = repository.item(itemId)
            _liveData.postValue(item.text)
        }
    }

    fun update(itemId: Long, newText: String) {
        viewModelScope.launch(dispatcher) {
            val item = repository.item(itemId).copy(text = newText)
            repository.update(itemId, newText)
            withContext(dispatcherMain) {
                changeLiveDataWrapper.update(ItemMapper().mapItemToItemUi(item))
                comeback()
            }
        }
    }

    fun delete(itemId: Long) {
        viewModelScope.launch(dispatcher) {
            val item = repository.item(itemId)
            repository.delete(itemId)
            withContext(dispatcherMain) {
                changeLiveDataWrapper.delete(ItemMapper().mapItemToItemUi(item))
                comeback()
            }
        }
    }

    fun comeback() {
        clear.clearViewModel(this::class.java)
    }
}