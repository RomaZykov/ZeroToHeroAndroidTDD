package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.ViewModel

interface ViewModelFactory : ProvideViewModel {

    fun clear(vararg viewModelClasses: Class<out ViewModel>)

    class Base(private val provideViewModel: ProvideViewModel) : ViewModelFactory {

        private val map = mutableMapOf<Class<out ViewModel>, ViewModel>()

        override fun <T : ViewModel> viewModel(clazz: Class<out ViewModel>): T {
            return if (map.containsKey(clazz)) {
                map[clazz] as T
            } else {
                val viewModel = provideViewModel.viewModel(clazz) as T
                map.put(clazz, viewModel)
                viewModel
            }
        }

        override fun clear(vararg viewModelClasses: Class<out ViewModel>) {
            viewModelClasses.forEach {
                map.remove(it)
            }
        }
    }
}