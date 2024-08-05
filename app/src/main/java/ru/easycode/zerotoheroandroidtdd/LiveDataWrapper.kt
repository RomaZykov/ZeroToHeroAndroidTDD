package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface LiveDataWrapper {

    fun update(value: UiState)

    fun save(bundleWrapper: BundleWrapper.Save)

    fun liveData(): LiveData<UiState>

    class Base(private val _uiState: SingleLiveEvent<UiState> = SingleLiveEvent<UiState>()) : LiveDataWrapper {

        override fun update(value: UiState) {
            _uiState.value = value
        }

        override fun liveData(): LiveData<UiState> {
            return _uiState
        }

        override fun save(bundleWrapper: BundleWrapper.Save) {
            bundleWrapper.save(liveData().value!!)
        }
    }
}

interface BundleWrapper {

    interface Mutable : Save, Restore

    interface Save {
        fun save(uiState: UiState)
    }

    interface Restore {
        fun restore(): UiState
    }
}
