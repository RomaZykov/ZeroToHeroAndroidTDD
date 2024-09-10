package ru.easycode.zerotoheroandroidtdd.core

import junit.framework.TestCase.assertEquals

interface FakeNavigation : Navigation.Update {

    companion object {
        const val NAVIGATE = "Navigation#navigate"
    }

    fun checkScreen(expected: Screen)

    class Base(private val order: Order) : FakeNavigation {

        private val actual = mutableListOf<Screen>()

        override fun update(screen: Screen) {
            actual.add(screen)
            order.add(NAVIGATE)
        }

        override fun checkScreen(expected: Screen) {
            assertEquals(expected, actual.last())
        }
    }
}