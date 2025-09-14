package net.thechance.mena.wallet.data

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DummyTest {

    private val dummy = Dummy()

    @Test
    fun testAdd() {
        val result = dummy.add(2, 3)
        assertEquals(5, result, "2 + 3 should equal 5")
    }

    @Test
    fun testIsEvenTrue() {
        assertTrue(dummy.isEven(4), "4 should be even")
    }

    @Test
    fun testIsEvenFalse() {
        assertFalse(dummy.isEven(5), "5 should not be even")
    }
}