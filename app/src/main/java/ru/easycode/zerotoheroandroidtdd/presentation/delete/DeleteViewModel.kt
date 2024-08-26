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

class DeleteViewModel(
    private val repository: Repository.Delete,
    private val deleteLiveDataWrapper: ListLiveDataWrapper.Delete,
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

    fun delete(itemId: Long) {
        viewModelScope.launch(dispatcher) {
            val item = repository.item(itemId)
            repository.delete(itemId)
            withContext(dispatcherMain) {
                deleteLiveDataWrapper.delete(ItemMapper().mapItemToItemUi(item))
            }
        }
        comeback()
    }

    fun comeback() {
        clear.clearViewModel(this::class.java)
    }
}
