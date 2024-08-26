package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.ViewModel

interface ViewModelFactory : ProvideViewModel, ClearViewModel {

    class Base(private val provideViewModel: ProvideViewModel, private val clear: ClearViewModel) :
        ViewModelFactory {

        private val map = mutableMapOf<Class<out ViewModel>, ViewModel>()

        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
            return if (map.containsKey(viewModelClass)) {
                map[viewModelClass] as T
            } else {
                val viewModel = provideViewModel.viewModel(viewModelClass)
                map.put(viewModelClass, viewModel)
                viewModel
            }
        }

        override fun clearViewModel(clazz: Class<out ViewModel>) {
            map.remove(clazz)
        }
    }
}