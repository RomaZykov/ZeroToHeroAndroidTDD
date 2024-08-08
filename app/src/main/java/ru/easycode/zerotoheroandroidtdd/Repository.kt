package ru.easycode.zerotoheroandroidtdd

import kotlinx.coroutines.delay

interface Repository {

    suspend fun load(): SimpleResponse

    class Base(
        private val service: SimpleService,
        private val url: String
    ) : Repository {

        override suspend fun load(): SimpleResponse {
            return service.fetch(url)
        }
    }
}