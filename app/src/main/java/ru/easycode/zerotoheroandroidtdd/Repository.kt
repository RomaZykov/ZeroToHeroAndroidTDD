package ru.easycode.zerotoheroandroidtdd

import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout

interface Repository {

    suspend fun load()

    class Base() : Repository {

        override suspend fun load() {
            withTimeout(3500) {
                delay(3000)
            }
        }
    }
}
