package net.thechance.mena.dukan.domain

class TestHandler {
    fun test(): String {
        println("Hello from Test Handler")
        return "Test Handler"
    }
}


val testHandler: TestHandler by lazy {
    TestHandler()
}