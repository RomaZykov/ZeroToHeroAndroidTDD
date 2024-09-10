package ru.easycode.zerotoheroandroidtdd.main

import androidx.lifecycle.LiveData
import org.junit.Test
import ru.easycode.zerotoheroandroidtdd.core.FakeNavigation
import ru.easycode.zerotoheroandroidtdd.core.Navigation
import ru.easycode.zerotoheroandroidtdd.core.Order
import ru.easycode.zerotoheroandroidtdd.core.Screen
import ru.easycode.zerotoheroandroidtdd.folder.list.FoldersListScreen

class MainViewModelTest {

    @Test
    fun test() {
        val order = Order()
        val navigation = FakeMutableNavigation.Base(FakeNavigation.Base(order))
        val viewModel = MainViewModel(
            navigation = navigation
        )

        viewModel.init(firstRun = true)
        navigation.checkScreen(FoldersListScreen)
    }
}

private interface FakeMutableNavigation : FakeNavigation, Navigation.Mutable {

    class Base(private val fakeNavigation: FakeNavigation) : FakeMutableNavigation {

        override fun update(list: Screen) {
            fakeNavigation.update(list)
        }

        override fun liveData(): LiveData<Screen> {
            throw Exception("Not used")
        }

        override fun checkScreen(expected: Screen) {
            fakeNavigation.checkScreen(expected)
        }
    }
}