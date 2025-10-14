package net.thechance.mena.faith.presentation.util

import org.jetbrains.compose.resources.StringResource

interface ResourceProvider {
    suspend fun getString(resource: StringResource, vararg formatArgs: Any): String
}

class StringResourceProvider : ResourceProvider {
    override suspend fun getString(resource: StringResource, vararg formatArgs: Any): String {
        return if (formatArgs.isEmpty()) org.jetbrains.compose.resources.getString(resource) else org.jetbrains.compose.resources.getString(resource, *formatArgs)
    }
}