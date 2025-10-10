package net.thechance.mena.faith.data.utils

class KnuthMorrisPrattSearchAlgorithm : SearchAlgorithm {
    override fun isContainsQuery(
        text: String,
        query: String,
    ): Boolean {
        val longestPrefixStringArray = computeLongestPrefixStringArray(query)
        return knuthMorrisPrattSearch(text, query, longestPrefixStringArray)
    }

    private fun computeLongestPrefixStringArray(pattern: String): IntArray {
        val patternLength = pattern.length
        val longestPrefixString = IntArray(patternLength)
        var longestPrefixStringLength = 0
        var indexOfComparison = 1
        longestPrefixString[0] = 0

        while (indexOfComparison < patternLength) {
            if (pattern[indexOfComparison] == pattern[longestPrefixStringLength]) {
                longestPrefixStringLength++
                longestPrefixString[indexOfComparison] = longestPrefixStringLength
                indexOfComparison++
            } else {
                if (longestPrefixStringLength != 0) {
                    longestPrefixStringLength = longestPrefixString[longestPrefixStringLength - 1]
                } else {
                    longestPrefixString[indexOfComparison] = 0
                    indexOfComparison++
                }
            }
        }
        return longestPrefixString
    }

    private fun knuthMorrisPrattSearch(
        text: String,
        pattern: String,
        longestPrefixStringArray: IntArray,
    ): Boolean {
        val textLength = text.length
        val patternLength = pattern.length
        if (textLength == 0 || patternLength > textLength) return false
        var textIndex = 0
        var patternIndex = 0
        while (textIndex < textLength) {
            if (pattern[patternIndex] == text[textIndex]) {
                textIndex++
                patternIndex++
            }

            if (patternIndex == patternLength) {
                return true
            } else if (textIndex < textLength && pattern[patternIndex] != text[textIndex]) {
                if (patternIndex != 0) {
                    patternIndex = longestPrefixStringArray[patternIndex - 1]
                } else {
                    textIndex++
                }
            }
        }
        return false
    }
}
