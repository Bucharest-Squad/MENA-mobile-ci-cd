package net.thechance.mena.dukan.domain

import kotlin.test.Test
import kotlin.test.assertSame

class TestClassTest {

    @Test
    fun test() {
        assertSame("TestClass from Domain Module", TestClass().test())
    }
}