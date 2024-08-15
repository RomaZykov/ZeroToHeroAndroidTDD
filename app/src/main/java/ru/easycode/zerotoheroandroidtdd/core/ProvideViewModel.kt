package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import ru.easycode.zerotoheroandroidtdd.create.CreateViewModel
import ru.easycode.zerotoheroandroidtdd.list.ListViewModel
import ru.easycode.zerotoheroandroidtdd.main.MainViewModel
import java.lang.IllegalStateException

interface ProvideViewModel {
    fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T

    class Base(private val liveDataWrapper: ListLiveDataWrapper.All, private val clear: ClearViewModel) : ProvideViewModel {

        // Warning use only one instance
        private val navigation = Navigation.Base()

        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
            return when (viewModelClass) {
                MainViewModel::class.java -> {
                    MainViewModel(navigation)
                }

                CreateViewModel::class.java -> {
                    CreateViewModel(liveDataWrapper, navigation, clear)
                }

                ListViewModel::class.java -> {
                    ListViewModel(liveDataWrapper, navigation)
                }

                else -> throw IllegalStateException("unknown type of viewModel")
            } as T
        }

        // Warning!!! Incorrect initializing
//        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
//            return when (viewModelClass) {
//                MainViewModel::class.java -> {
//                    MainViewModel(Navigation.Base()) // !!! Navigation.Base()
//                }
//
//                CreateViewModel::class.java -> {
//                    CreateViewModel(liveDataWrapper, Navigation.Base(), ClearViewModel.Base()) // !!! Navigation.Base()
//                }
//
//                ListViewModel::class.java -> {
//                    ListViewModel(liveDataWrapper, Navigation.Base()) // !!! Navigation.Base()
//                }
//
//                else -> throw IllegalStateException("unknown type of viewModel")
//            } as T
//        }
    }
}