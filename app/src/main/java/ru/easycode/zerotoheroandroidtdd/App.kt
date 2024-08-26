package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.core.ClearViewModel
import ru.easycode.zerotoheroandroidtdd.core.Now
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.core.ViewModelFactory
import ru.easycode.zerotoheroandroidtdd.data.ItemsDataBase
import ru.easycode.zerotoheroandroidtdd.data.cache.LocalDataSource

class App : Application(), ProvideViewModel {

    private lateinit var factory: ViewModelFactory
    private lateinit var localDataSource: LocalDataSource
    private val clear = object : ClearViewModel {
        override fun clearViewModel(clazz: Class<out ViewModel>) {
            factory.clearViewModel(clazz)
        }
    }

    override fun onCreate() {
        super.onCreate()
        localDataSource = LocalDataSource.Base(ItemsDataBase.getInstance(this).itemsDao())
        val provideViewModel = ProvideViewModel.Base(localDataSource, Now.Base(), clear)
        factory = ViewModelFactory.Base(provideViewModel, clear)
    }

    override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
        return factory.viewModel(viewModelClass)
    }
}