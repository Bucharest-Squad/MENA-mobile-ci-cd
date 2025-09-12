package net.thechance.mena.dukan.domain

import kotlin.test.Test
import kotlin.test.assertSame

class TestHandlerTest {

    @Test
    fun test() {
        assertSame("Test Handler", TestHandler().test())
    }
}