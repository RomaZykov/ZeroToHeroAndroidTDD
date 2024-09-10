package ru.easycode.zerotoheroandroidtdd.core

interface Now {
    fun timeInMillis(): Long

    class Base() : Now {
        override fun timeInMillis(): Long {
            return System.currentTimeMillis()
        }
    }
}
