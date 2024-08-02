package ru.easycode.zerotoheroandroidtdd

interface Load {

    fun initial(): UiState

    fun load(): UiState

    fun result(): UiState

    class Base(private val text: String) : Load {
        override fun initial(): UiState {
            return UiState.Base(text)
        }

        override fun load(): UiState {
            return UiState.Load(text)
        }

        override fun result(): UiState {
            return UiState.Result(text)
        }
    }
}