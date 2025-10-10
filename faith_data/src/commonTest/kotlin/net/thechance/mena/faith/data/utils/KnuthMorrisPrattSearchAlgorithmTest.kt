package net.thechance.mena.faith.data.utils

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class KnuthMorrisPrattSearchAlgorithmTest {
    private val kmpAlgorithm = KnuthMorrisPrattSearchAlgorithm()

    @Test
    fun `test pattern found at beginning of text`() {
        assertTrue(kmpAlgorithm.isContainsQuery("hello world", "hello"))
    }

    @Test
    fun `test pattern found in middle of text`() {
        assertTrue(kmpAlgorithm.isContainsQuery("hello world", "world"))
    }

    @Test
    fun `test pattern found at end of text`() {
        assertTrue(kmpAlgorithm.isContainsQuery("hello world", "world"))
    }

    @Test
    fun `test pattern not found in text`() {
        assertFalse(kmpAlgorithm.isContainsQuery("hello world", "xyz"))
    }

    @Test
    fun `test empty text returns false`() {
        assertFalse(kmpAlgorithm.isContainsQuery("", "pattern"))
    }

    @Test
    fun `test empty pattern on non-empty text returns false`() {
        assertFalse(kmpAlgorithm.isContainsQuery("hello", " "))
    }

    @Test
    fun `test pattern longer than text returns false`() {
        assertFalse(kmpAlgorithm.isContainsQuery("hi", "hello"))
    }

    @Test
    fun `test pattern equals text returns true`() {
        assertTrue(kmpAlgorithm.isContainsQuery("hello", "hello"))
    }

    @Test
    fun `test single character pattern found`() {
        assertTrue(kmpAlgorithm.isContainsQuery("hello", "e"))
    }

    @Test
    fun `test single character pattern not found`() {
        assertFalse(kmpAlgorithm.isContainsQuery("hello", "z"))
    }

    // ============ Repeating Pattern Tests ============
    @Test
    fun `test pattern with repeating prefix and suffix`() {
        assertTrue(kmpAlgorithm.isContainsQuery("ababab", "abab"))
    }

    @Test
    fun `test pattern AAAA in AAAAAAA`() {
        assertTrue(kmpAlgorithm.isContainsQuery("aaaaaaa", "aaaa"))
    }

    @Test
    fun `test overlapping pattern ABAB in ABABABAB`() {
        assertTrue(kmpAlgorithm.isContainsQuery("abababab", "abab"))
    }

    @Test
    fun `test pattern with internal repetition`() {
        assertTrue(kmpAlgorithm.isContainsQuery("aabaab", "aabaab"))
    }

    @Test
    fun `test repeating character not matching`() {
        assertFalse(kmpAlgorithm.isContainsQuery("aaaa", "bbb"))
    }
}