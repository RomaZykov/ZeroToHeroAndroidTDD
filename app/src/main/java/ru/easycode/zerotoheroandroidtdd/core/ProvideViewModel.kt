package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import ru.easycode.zerotoheroandroidtdd.data.Repository
import ru.easycode.zerotoheroandroidtdd.data.cache.LocalDataSource
import ru.easycode.zerotoheroandroidtdd.presentation.add.AddViewModel
import ru.easycode.zerotoheroandroidtdd.presentation.delete.DeleteViewModel
import ru.easycode.zerotoheroandroidtdd.presentation.main.MainViewModel

interface ProvideViewModel {

    fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T

    class Base(
        private val dataSource: LocalDataSource,
        private val now: Now,
        private val clearViewModel: ClearViewModel
    ) : ProvideViewModel {

        private val repository = Repository.Base(dataSource, now)
        private val liveDataWrapper = ListLiveDataWrapper.Base()
        private val dispatcher = Dispatchers.IO
        private val dispatcherMain = Dispatchers.Main

        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
            return when (viewModelClass) {
                MainViewModel::class.java -> {
                    MainViewModel(repository, liveDataWrapper, dispatcher, dispatcherMain)
                }

                AddViewModel::class.java -> {
                    AddViewModel(
                        repository,
                        liveDataWrapper,
                        clearViewModel,
                        dispatcher,
                        dispatcherMain
                    )
                }

                DeleteViewModel::class.java -> {
                    DeleteViewModel(
                        repository,
                        liveDataWrapper,
                        clearViewModel,
                        dispatcher,
                        dispatcherMain
                    )
                }

                else -> throw Exception("unknown type of viewModel")
            } as T
        }
    }
}