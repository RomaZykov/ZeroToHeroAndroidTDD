package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.core.ClearViewModels
import ru.easycode.zerotoheroandroidtdd.core.Now
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.core.ViewModelFactory
import ru.easycode.zerotoheroandroidtdd.main.AppDataBase

class App : Application(), ProvideViewModel {

    private lateinit var factory: ViewModelFactory
    private val clear = object : ClearViewModels {
        override fun clear(vararg viewModelClasses: Class<out ViewModel>) {
            factory.clear(*viewModelClasses)
        }
    }

    override fun onCreate() {
        super.onCreate()

        factory = ViewModelFactory.Base(
            provideViewModel = ProvideViewModel.Base(
                foldersDao = AppDataBase.getInstance(this).foldersDao(),
                notesDao = AppDataBase.getInstance(this).notesDao(),
                now = Now.Base(),
                clear = clear
            )
        )
    }

    override fun <T : ViewModel> viewModel(clazz: Class<out ViewModel>): T {
        return factory.viewModel(clazz)
    }
}