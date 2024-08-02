package ru.easycode.zerotoheroandroidtdd

interface Count {

    fun initial(number: String): UiState

    fun increment(number: String): UiState

    fun decrement(number: String): UiState

    class Base(private val step: Int, private val max: Int, private val min: Int) : Count {

        init {
            if (step < 1)
                throw IllegalStateException("step should be positive, but was $step")
            if (max < 0)
                throw IllegalStateException("max should be positive, but was $max")
            if (max == 0)
                throw IllegalStateException()
            if (step > max)
                throw IllegalStateException("max should be more than step")
        }

        override fun initial(number: String): UiState {
            return if (number.toInt() <= min) {
                UiState.Min(min.toString())
            } else if (number.toInt() >= max) {
                UiState.Max(number)
            } else {
                UiState.Base(number)
            }
        }

        override fun decrement(number: String): UiState {
            val decrementedResult = number.toInt() - step
            if (decrementedResult <= min) {
                val currentMin = if (decrementedResult - step < min) min else decrementedResult
                return UiState.Min(currentMin.toString())
            }
            return UiState.Base(decrementedResult.toString())
        }

        override fun increment(number: String): UiState {
            val incrementedResult = number.toInt() + step
            if (incrementedResult >= max || incrementedResult + step > max) {
                val currentMax =
                    if (incrementedResult + step > max) incrementedResult else incrementedResult - step
                return UiState.Max(currentMax.toString())
            }
            return UiState.Base(incrementedResult.toString())
        }
    }
}