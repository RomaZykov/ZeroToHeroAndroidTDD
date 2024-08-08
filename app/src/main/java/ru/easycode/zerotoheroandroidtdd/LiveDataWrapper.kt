package ru.easycode.zerotoheroandroidtdd

import android.annotation.SuppressLint
import android.os.Build
import android.os.Build.VERSION_CODES.TIRAMISU
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.Serializable

interface LiveDataWrapper {

    fun update(value: UiState)

    fun save(bundleWrapper: BundleWrapper.Save)

    fun liveData(): LiveData<UiState>

    class Base(private val _uiState: MutableLiveData<UiState> = SingleLiveEvent()) :
        LiveDataWrapper {

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

interface BundleWrapper : Serializable {

    interface Mutable : Save, Restore

    interface Save {
        fun save(uiState: UiState)
    }

    interface Restore {
        fun restore(): UiState
    }

    class Base(private val bundle: Bundle) : Mutable {
        override fun save(uiState: UiState) {
            bundle.putSerializable("isStateSaved", uiState)
        }

        override fun restore(): UiState {
            return if (Build.VERSION.SDK_INT >= TIRAMISU) {
                bundle.getSerializable("isStateSaved", UiState::class.java) as UiState
            } else {
                bundle.getSerializable("isStateSaved") as UiState
            }
        }
    }
}