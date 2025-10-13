package net.thechance.mena.faith.presentation.util

import org.jetbrains.compose.resources.StringResource

interface ResourceProvider {
    suspend fun getString(resource: StringResource, vararg formatArgs: Any): String
}

class DefaultResourceProvider : ResourceProvider {
    override suspend fun getString(resource: StringResource, vararg formatArgs: Any): String {
        return if (formatArgs.isEmpty()) getString(resource) else getString(resource, *formatArgs)
    }
}