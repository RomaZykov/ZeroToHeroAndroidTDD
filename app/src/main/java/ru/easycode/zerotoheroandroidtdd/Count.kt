package ru.easycode.zerotoheroandroidtdd

import java.io.Serializable

interface Count : Serializable {

    override fun toString(): String

    override fun equals(other: Any?): Boolean

    fun increment(): Count

    fun decrement(): Count

    fun isMax(): Boolean

    fun isMin(): Boolean

    class Base(
        private val value: Int,
        private val step: Int,
        private val min: Int,
        private val max: Int
    ) : Count {

        override fun decrement(): Count {
            return if (!isMin() && value - step >= min) {
                Base(value - step, step, min, max)
            } else {
                this
            }
        }

        override fun isMax(): Boolean {
            return value >= max
        }

        override fun isMin(): Boolean {
            return value <= min
        }

        override fun equals(other: Any?): Boolean {
            return this.step == (other as Base).step && this.max == other.max && this.min == other.min
        }

        override fun toString(): String {
            return value.toString()
        }

        override fun increment(): Count {
            return if (!isMax() && value + step <= max) {
                Base(value + step, step, min, max)
            } else {
                this
            }
        }

        override fun hashCode(): Int {
            var result = value
            result = 31 * result + step
            return result
        }
    }
}