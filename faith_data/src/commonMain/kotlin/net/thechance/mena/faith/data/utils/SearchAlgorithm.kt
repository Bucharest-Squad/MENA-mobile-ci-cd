package net.thechance.mena.faith.data.utils

interface SearchAlgorithm {
    fun isContainsQuery(
        text: String,
        query: String,
    ): Boolean
}
