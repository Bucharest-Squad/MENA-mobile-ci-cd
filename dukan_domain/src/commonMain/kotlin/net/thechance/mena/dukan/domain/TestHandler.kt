package net.thechance.mena.dukan.domain

class TestHandler {
    fun test(): String {
        return "Test Handler"
    }
}


val testHandler: TestHandler by lazy {
    TestHandler()
}