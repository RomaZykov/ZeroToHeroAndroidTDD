package ru.easycode.zerotoheroandroidtdd.core

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import ru.easycode.zerotoheroandroidtdd.main.MainViewModel
import java.lang.IllegalStateException

interface ViewModelFactory : ProvideViewModel, ClearViewModel {

    class Base(private val provideViewModel: ProvideViewModel) : ViewModelFactory {
        private var cachedVMs: MutableMap<Class<out ViewModel>, ViewModel> = mutableMapOf()

        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
            return if (cachedVMs.containsKey(viewModelClass)) {
                cachedVMs[viewModelClass] as T
            } else {
                val viewModel = provideViewModel.viewModel(viewModelClass)
                cachedVMs[viewModelClass] = viewModel
                viewModel
            }
        }

        override fun clear(viewModelClass: Class<out ViewModel>) {
            cachedVMs.remove(viewModelClass)
        }
    }
}
