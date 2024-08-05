package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import java.security.PrivateKey

interface Repository {

    suspend fun load()

    class Base() : Repository {

        override suspend fun load() {
            delay(3500)
        }
    }
}