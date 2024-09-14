package ru.easycode.zerotoheroandroidtdd

import java.io.Serializable

interface Count : Serializable {

    override fun toString(): String

    override fun equals(other: Any?): Boolean

    fun increment(): Count

    class Base(private val value: Int, private val step: Int) : Count {
        override fun equals(other: Any?): Boolean {
            return this.step == (other as Base).step
        }

        override fun toString(): String {
            return value.toString()
        }

        override fun increment(): Count {
            return Base(value + step, step)
        }

        override fun hashCode(): Int {
            var result = value
            result = 31 * result + step
            return result
        }
    }
}
