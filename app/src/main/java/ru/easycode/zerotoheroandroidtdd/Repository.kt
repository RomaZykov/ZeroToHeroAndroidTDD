package ru.easycode.zerotoheroandroidtdd

import android.renderscript.Long4
import androidx.core.app.NotificationCompat.MessagingStyle.Message
import kotlinx.coroutines.delay
import java.io.IOException
import java.net.UnknownHostException

interface Repository {

    suspend fun load(): LoadResult

    class Base(
        private val service: SimpleService,
        private val url: String
    ) : Repository {

        override suspend fun load(): LoadResult {
            return try {
                LoadResult.Success(service.fetch(url))
            } catch (e: UnknownHostException) {
                LoadResult.Error(true)
            } catch (e: IllegalStateException) {
                LoadResult.Error(false)
            }
        }
    }
}