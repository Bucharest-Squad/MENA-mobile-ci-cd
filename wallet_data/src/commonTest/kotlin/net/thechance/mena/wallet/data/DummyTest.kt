package net.thechance.mena.wallet.data

import kotlin.test.Test
import kotlin.test.assertEquals

class DummyTest {

    @Test
    fun testAddition() {
        val dummy = DummyClass()
        assertEquals(4, dummy.add(2, 2))
    }
}