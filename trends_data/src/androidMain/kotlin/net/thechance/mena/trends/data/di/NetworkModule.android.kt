package net.thechance.mena.trends.data.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual fun createHttpEngine(): HttpClientEngine = OkHttp.create()