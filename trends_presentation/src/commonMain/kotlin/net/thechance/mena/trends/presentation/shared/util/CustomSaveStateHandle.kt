package net.thechance.mena.trends.presentation.shared.util

interface CustomSaveStateHandle {
    fun <T> get(key:String) : T?
    fun <T> set(key:String, value:T)
}